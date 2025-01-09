package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

public class Main {
    static Boolean hadError = false;
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        if(args.length > 1){
           System.out.println("Usage: jlox [scripts]");
           System.exit(64);
        }
        else if (args.length == 1){
            runFile(args[0]);
        }//jlox is a scripting language meaning that it runs the code strait from source, so we either give the intepreter a path to the file to run on the commmand lines or just the file directly with the tcode
        else{
            runPrompt();
        }//this just runs the prompt that we have
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        for (;;){
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false;//very good practice in engineering software to separate code that generates the errors from the code and the ones that report them

        }


    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

       //indicate that and error occured in our exiting code
        if(hadError) System.exit(64);
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();//we need to implement this class later, don't worry about it now!
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    static void error(int line, String message){
        report(line, "", message);
    }
   private static void report(int line, String where, String message){
        System.out.println("[Line " + line + "] " + where +": "+ message);
        hadError = true;
   }



}