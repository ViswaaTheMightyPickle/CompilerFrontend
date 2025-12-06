import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lexer {
    private final String input;

    public Lexer(String input) {
        this.input = input;
    }

    private int index = 0;
    private int line = 1;
    private int column = 1;

    private static Set<String> Keywords = new HashSet<String>();
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

    private static Set<Character> Numbers = new HashSet<Character>();
    static {
        Character[] numbers = {
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
                '.', 'e', 'x', 'o'
        };

        Numbers.addAll(Arrays.asList(numbers));
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

    char peekPrevious()
    {
        return input.charAt(index-1);
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
//        int start = index;
//
//        next();
//
//        while(!isAtEnd()) {
//            if(isDigit(peek())) {
//                next();
//            }
//            else if(peek() == '.') {
//                next();
//            }
//            else if(peek() == 'e' || peek() == 'E') {
//                next();
//            }
//            else
//                break;
//        }
//
//        String text = input.substring(start, index);
//
//        return new Token(TokenType.NUMERIC_LITERAL, text, line, column);
    }

    Token readOperator(){
//        int start = index;
//
//        switch(peek()) {
//            case '+', '-', '*', '/', '^', '%':
//            {
//                return new Token(TokenType.OPERATOR, Character.toString(peek()), line, column);
//            }
//            default:
//            {
//                if(peek() == '<'){
//                    next();
//                    if(peek() == '-' || peek() == '='){
//                        next();
//                        String text = input.substring(start, index);
//                        return new Token(TokenType.OPERATOR, text, line, column);
//                    }
//                }
//
//                if(peek() == '>'){
//                    next();
//                    if(peek() == '='){
//                        next();
//                        String text = input.substring(start, index);
//                        return new Token(TokenType.OPERATOR, text, line, column);
//                    }
//                }
//            }
//        }
//        return null;
    }


}
