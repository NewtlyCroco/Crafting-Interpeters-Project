package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.example.TokenType.*;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();

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
            start = current;
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

            default:
                Main.error(line, "Unexpected Character");
                break;//super importatnt to our program and in conjunciton with the advance method, we keep scanning until the whole source file is scanned so that user does not have to play whack a mole with the errors they are receiveing and can rather see all the errors at once produced by the compiler!

        }
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
}
