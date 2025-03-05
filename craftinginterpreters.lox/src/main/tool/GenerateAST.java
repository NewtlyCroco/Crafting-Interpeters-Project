import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class GenerateAST {
   public static void main(String[] args) throws IOException {
    if (args.length != 1){
        System.err.println("Usage: generate AST <Output Directory>");
        System.exit(64);

    }
    String outputDir = args[0];
    defineAst(outputDir, "Expr", Arrays.asList(
       "Binary     : Expr left, Token operator, Expr right",
            "Grouping   : Expr expression",
            "Literal    : Object Value",
            "Unary      : Token operator, Expr right"
    ));
   }
   

   private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
    String path = outputDir + "/" + baseName + ".java";
    PrintWriter writer = new PrintWriter(path, "UTF-8");

    writer.println("package craftinginterpeters.lox;");
    writer.println();
    writer.println("import java.util.List;");
    writer.println();
    writer.println("abstract class " + baseName + " {");
    for(String type: types){
        String className = type.split(":")[0].trim();
        String fields = type.split(":")[1].trim();
        defineType(writer, baseName, className, fields);
    }
    writer.println("}");
    writer.close(); 
   }
   private static void defineType(PrinterWriter writer, String baseName, String className, String fieldList){
    writer.println("  static class " + className + " extends " + baseName + " {");
   }//this is where i stopped for the night!


}
