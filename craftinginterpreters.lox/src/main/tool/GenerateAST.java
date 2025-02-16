import java.io.IOException;
import java.util.Arrays;

public class GenerateAST {
   public static void main(String[] args) throws IOException {
    if (args.length != 1){
        System.err.println("Usage: generate AST <Output Directory>");
        System.exit(64);

    }
    String outputDir = args[0];
    defineAst(outputDir, "Expr", Arrays.asList(
        "Binary    : Expr left, Token operator, Expr right",
            "Grouping   : Expr expression",
            "Literal    : Object Value",
            "Unary      : Token operator, Expr right"
    ));
   } 
}
