package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.TokenType.*;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private static final Map<String, TokenType> keywords;

    //introduces the idea of maximal munch, we need to be able to check what rule basically the section of raw code matches THE MOST to determine what lexeme it is, for example if the word orchid appears, then it could possibley be broken into the lexeme of or and chid but we wouldnt want that

    static {
        keywords = new HashMap<>();
        keywords.put("and", AND);
        keywords.put("class", CLASS);
        keywords.put("else", ELSE);
        keywords.put("false",FALSE);
        keywords.put("for", FOR);
        keywords.put("if", IF);
        keywords.put("fun", FUN);
        keywords.put("nil", NIL);
        keywords.put("or", OR);
        keywords.put("print", PRINT);
        keywords.put("return", RETURN);
        keywords.put("super", SUPER);
        keywords.put("this", THIS);
        keywords.put("true", TRUE);
        keywords.put("var", VAR);
        keywords.put("while", WHILE);
    }

    private int start = 0;
    private int current = 0;
    private int line = 1;




    Scanner(String source){
        this.source = source;
    }

   private boolean isAtEnd() {
        return current >= source.length();
   }

    List<Token> scanTokens(){
        while(!isAtEnd()){
            start = current;//restart were the start index is so that we can resuse the current index to move and scane the next possible lexeme
            scanToken();
        }
        tokens.add(new Token(EOF, "",null, line));
        return tokens;
    }


    private void scanToken() {
        char c = advance();
        switch (c){
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG); break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL); break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS); break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER); break;
            case '/':
                if (match('/')){
                    while (peek() != '\n' && !isAtEnd()) advance();
                }
                else{
                    addToken(SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                break;

            case '\n':
                line++;
                break;//very important, when we see a new line character we want to move the line so that our start, end, current can do its job right and we can properly track errors as well in our tokens!

            case '"':
                string(); break;


            default:
                if (isDigit(c)){
                    number();
                } else if (isAlpha(c)){
                    identifier();
                }
                else {
                    Main.error(line, "Unexpected Character");
                }
                break;//super important to our program and in conjunction with the advance method, we keep scanning until the whole source file is scanned so that user does not have to play whack a mole with the errors they are receiveing and can rather see all the errors at once produced by the compiler!
        }
    }

    private void identifier(){
        while (isAlphaNumeric(peek())) advance();
        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if(type == null) type = IDENTIFIER;
        addToken(type);//this identifying is extremely important, and allows us to differentiate lexemes and reserved words from strings/invalid characters or just other occurences of reserved words in weird places or inside other words
    }

    private boolean isAlpha(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    private boolean isAlphaNumeric(char c){
        return isAlpha(c) || isDigit(c);
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);//just a mini version of our match, just looking at what the current character is ahead of our currently scanning character.

    }

    private boolean match(char expected){
        if(isAtEnd()) return false;
        if (source.charAt(current) == expected) return false;

        current++;
        return true;//looks confusing but is not! our current "pointer" sits on the character after the one we are actually looking at as seen in the advance method, so by looking and matching the actual current variable pointer to what we want to match, we are looking ahead to see if the next character is something we want it to be!
    }

    private char advance(){
        current++;
        return source.charAt(current - 1);
    }
    private void addToken(TokenType type){
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal){
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private void string(){
        while(peek() != '"' && !isAtEnd()) {
            if(peek() == '\n') line++;

        }
        if(isAtEnd()) {
            Main.error(line, "Unexpected end of string");
            return;
        }

        advance();//this is to advance the pointer past the quote to ensure proper continued reading
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private boolean isDigit(char c){
        return c  >= '0' && c <= '9';
    }

    private void number(){
        while (isDigit(peek())) advance();

        if(peek() == '.' && isDigit(peekNext())){
            advance();
            while (isDigit(peek())) advance();
        }
        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private char peekNext(){
        if(current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }
}
