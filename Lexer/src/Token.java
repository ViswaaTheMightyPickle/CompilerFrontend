public class Token {

    private TokenType tokenType;
    private String value;
    private final int line;
    private final int column;

    public Token(TokenType tokenType, String value, int line, int column) {
        this.tokenType = tokenType;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return tokenType.toString() + "(" + value + ") at " + line + ":" + column;
    }
}
