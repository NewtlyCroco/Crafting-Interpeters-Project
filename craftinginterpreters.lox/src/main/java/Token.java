public class Token {
    final TokenType type;//this is the basis of all lexical analysis work, over the entirety of our source files, we break up the lines into stuff actually readable, and occasically spit out tokens to be processed if there is proper logic, then we process the logic and so on! How facinating!
    final String lexeme;
    final Object literal;
    final int line;//this is where are simple interpreter will actually keep track of the errors, keeping in memory of each token the line that it is for error reporting, this would be more sophsisticated for a better interpreter

    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }


    public String toString(){
        return type.toString() + " " + lexeme + " " + literal;
    }
}
