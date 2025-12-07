import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Lexer {
    private final String input;

    public Lexer(String input) {
        this.input = input;
    }

    private int index = 0;
    private int line = 1;
    private int column = 1;

    private static final Set<String> Keywords = new HashSet<>();
    static {
        String[] words = {
                "num", "dec", "character", "null", "bool",
                "if", "then", "while", "for", "do",
                "foreach", "in", "define", "class", "pull",
                "leading", "ex", "dynamic", "xo", "xh", "xb",
                "and", "or", "not", "equal"
        };

        Keywords.addAll(Arrays.asList(words));
    }

    char peek() {
        if (index >= input.length()) return '\0';
        return input.charAt(index);
    }

    char next() {
        if (index >= input.length()) return '\0';
        char ch = input.charAt(index++);
        if (ch == '\n') {line++; column = 1;}
        else column++;
        return ch;
    }

    boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    boolean isLetter(char ch) {
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
    }

    boolean isIdentifierStart(char ch) {
        return ch == '_';
    }
    
    boolean isOperatorStart(char ch) {
        return  ch == '+'
                || ch == '-'
                || ch == '*'
                || ch == '/'
                || ch == '%'
                || ch == '^'
                || ch == '>'
                || ch == '|'
                || ch == '<'
                || ch == '='
                || ch == '.';
    }

    private static final HashSet<Character> Punctuations = new HashSet<>();
    static {
        Character[] punctuations = {
                '(',
                ')',
                '[',
                ']',
                '{',
                '}',
                ';',
                ','
        };
        Punctuations.addAll(Arrays.asList(punctuations));
    }

    boolean isPunctuation(char ch) {
        return Punctuations.contains(ch);
    }

    boolean isComment(char ch) {
        return '~' == ch;
    }

    boolean isWhitespace(char ch) {
        return ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r';
    }

    boolean isIdentifierPart(char ch) {
        //can be start, not space, any letters and numbers
        return isIdentifierStart(ch) || isLetter(ch) || isDigit(ch);
    }

    boolean isAtEnd() {
        return index >= input.length();
    }

    void skipWhitespace() {
        while(!isAtEnd() && isWhitespace(peek())) {
            next();
        }
    }

    Token readPunctuation() {
        String text = "";
        if(!isAtEnd() && isPunctuation(peek())) {
            text += next();
        }
        if(!text.isEmpty()) return new Token(TokenType.PUNCTUATION, text, line, column);
        else throw new RuntimeException("Punctuation token declared empty.");
    }

    void skipComment()
    {
        next();

        while(!isAtEnd() && !isComment(peek())) {
            next();
        }

        if(isAtEnd()) {
            throw new RuntimeException("Comment not terminated.");
        }

        next();
    }

    Token readIdentifier() {
        int start = index;
        next();

        while(!isAtEnd() && isIdentifierPart(peek())) { next(); }

        String text = input.substring(start, index);

        if(Keywords.contains(text)) {
            return new Token(TokenType.KEYWORD, text, line, column);
        }

        return new Token(TokenType.IDENTIFIER, text, line, column);
    }

    Token readNumber(){
        int start = index;

        while(!isAtEnd() && isDigit(peek())) {
            next();
        } //integeral part

        boolean seenDecimal = false;

        if(!isAtEnd() && peek() == '.'){
            seenDecimal = true;
            next();
            if(!isDigit(peek())) throw new RuntimeException("No number followed after the decimal declaration at: " + line + ", " + column);

            while(!isAtEnd() && isDigit(peek())) {
                next();
            }
        }

        boolean seenExponent = false;

        if (!isAtEnd() && (peek() == 'e' || peek() == 'E')){
            seenExponent = true;
            next();
        }

        if(seenExponent) {
            if(!isAtEnd() && (peek() == '+' || peek() == '-')){
                next();
            }

            if(!isAtEnd() && (isDigit(peek()))){
                next();
                while(!isAtEnd() && isDigit(peek())) {
                    next();
                }
            }
            else{
                throw new RuntimeException("No number followed after the exponent declaration at: " + line + ", " + column);
            }
        }

        String text = input.substring(start, index);

        int decimalCounter = 0;
        for(int i = 0; i < text.length(); i++){
            char ch = text.charAt(i);
            if(ch == '.') decimalCounter++;
        }

        if(decimalCounter > 1){
            throw new RuntimeException("Too many decimal declarations in one statement at: " + line + ", " + column);
        }

        if(text.charAt(0) == '.'){
            if(text.length() == 1 || !isDigit(text.charAt(1))){
                throw new RuntimeException("Invalid decimal declaration at: " + line + ", " + column);
            }
            text = "0" + text;
        }

        int exponentCounter = 0;
        for(int i = 0; i < text.length(); i++){
            char ch = text.charAt(i);
            if(ch == 'e' || ch == 'E') exponentCounter++;
        }

        if(exponentCounter > 1){
            throw new RuntimeException("Too many exponent declarations in one statement at: " + line + ", " + column);
        }

        if(!seenExponent){
            if(text.contains("+") || text.contains("-")){
                throw new RuntimeException("Invalid numerical declaration at: " + line + ", " + column);
            }
        }

        return new Token(TokenType.NUMERIC_LITERAL, text, line, column);
    }

    private static final HashSet<Character> DOUBLE_OPERATORS = new HashSet<>();
    static {
        DOUBLE_OPERATORS.add('<');
        DOUBLE_OPERATORS.add('>');
        DOUBLE_OPERATORS.add('+');
        DOUBLE_OPERATORS.add('-');
    }

    Token readOperator(){
        int start = index;
        String op = "";

        boolean condition = !isAtEnd() && isOperatorStart(peek());

        if(condition) {
            op += next();
        }

        if(!isAtEnd() && isOperatorStart(peek())) {
            if(DOUBLE_OPERATORS.contains(op.charAt(0))) {
                if(!isAtEnd() && isOperatorStart(peek())) {
                    switch(op.charAt(0)) {
                        case '<', '-' -> {
                            switch(peek())
                            {
                                case '-', '=' -> op += next();
                                default -> throw new RuntimeException("Invalid double operator at: " + line + ", " + column);
                            }
                        }

                        case '>' -> {
                            if (peek() == '=') {
                                op += next();
                            } else {
                                throw new RuntimeException("Invalid double operator at: " + line + ", " + column);
                            }
                        }

                        case '+' -> {
                            switch(peek())
                            {
                                case '+', '=' -> op += next();
                                default -> throw new RuntimeException("Invalid double operator at: " + line + ", " + column);
                            }
                        }
                    }
                }
            }
            else{
                throw new RuntimeException("Invalid double operator at: " + line + ", " + column);
            }
        }

        if(op.equals("=")) throw new RuntimeException("Invalid '=' operator at: " + line + ", " + column);

        return new Token(TokenType.OPERATOR, op, line, column);
    }
}
