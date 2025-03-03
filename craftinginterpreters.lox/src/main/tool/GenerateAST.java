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
    defineAst(outputDir, "Expr", Arrays.asList(//how is this different from the tokens we made with the scanner?
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
    writer.println("abstract class " + baseName + " {");// in our generate we are looking at splitting the actual productions we have defined and the even more spesific types, like binary etc.
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
    
    writer.println("    " + className + "(" + fieldList + ") {");
    //constructer hehe

    String[] fields = fieldsList.split(", ");
    for(String field : fields){
        String name = field.split(" ")[1];
        writer.println("    this." + name + " = " + name + ";");
        
    }

    writer.println("    }");

    writer.println();
    for(String field : fields){
        writer.println("    final " + field + ";");

    }
    writer.println(" }");//ok this is were I have stopped for the night! completeling just the simple script to generate all our data classes (with no actions or methods, to then be translated to a more logical language format with synatx trees and productions!)
    

}

}
