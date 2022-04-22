package MainPackage;

import java.util.ArrayList;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;

import ast.Node;
import ast.AssetLanVisitorImpl;

import parser.AssetLanLexer;
import parser.AssetLanParser;
import util.Environment;
import util.SemanticError;


public class Test {

	  public static void main(String[] args) throws Exception {
	      
		  String fileName = "src/mainPackage/prova.assetlan";

	        CharStream input = CharStreams.fromFileName(fileName);
	        
	        AssetLanLexer lexer = new AssetLanLexer(input);
		
	        
	        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
	        
			AssetLanParser parser = new AssetLanParser(tokenStream);
			
			
			//parser.removeErrorListeners(); // remove ConsoleErrorListener 
			//parser.addErrorListener(new VerboseListener());
			/* 
			lexer.lexerrors = new FileWriter("src/lexicalerrors.txt");
			lexer.lexerrors.write("");*/
			lexer.removeErrorListeners(); // remove ConsoleErrorListener 
			lexer.addErrorListener(new VerboseListener());
			
			
			int lexicalErrors = ((VerboseListener) (lexer.getErrorListeners().get(0))).getNumberErrors();
		
			parser.setBuildParseTree(true);
	        
	        ParseTree tree = parser.program(); // begin parsing at init rule
	        //System.out.println(tree.toStringTree(parser));
	        
	       // AssetLanBaseVisitor eval = new AssetLanBaseVisitor();
	       // eval.visit(tree);
	        
	        AssetLanVisitorImpl visitor = new AssetLanVisitorImpl();
	       
	        
	        Node ast = visitor.visit(tree); //generazione AST 
				
			//SIMPLE CHECK FOR LEXER ERRORS
			if (lexicalErrors > 0 || parser.getNumberOfSyntaxErrors() > 0){
				System.out.println("The program was not in the right format. Exiting the compilation process now");
			} else {
				Environment env = new Environment();
				ArrayList<SemanticError> err = ast.checkSemantics(env);
				if(err.size()>0){
					System.out.println("You had: " +err.size()+" errors:");
					for(SemanticError e : err)
						System.out.println("\t" + e);
				} else {
					System.out.println("Visualizing AST...");
					System.out.println(ast.toPrint(""));
			
				}
			}
	  }
	
}
