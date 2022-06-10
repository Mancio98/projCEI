package MainPackage;

import java.util.ArrayList;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;

import ast.Node;
import ast.type.Type;
import ast.type.VoidType;
import ast.AssetLanVisitorImpl;

import parser.AssetLanLexer;
import parser.AssetLanParser;
import util.EEnvironment;
import util.Environment;
import util.STEnvironment;
import util.SemanticError;


public class Test {

	  public static void main(String[] args) throws Exception {
	      
		  	String fileName = "src/mainPackage/input.assetlan";

	        CharStream input = CharStreams.fromFileName(fileName);
	        AssetLanLexer lexer = new AssetLanLexer(input);
	        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
			AssetLanParser parser = new AssetLanParser(tokenStream);
			
			//Remove default error listener
			lexer.removeErrorListeners(); 
			lexer.addErrorListener(new VerboseListener(false));
			parser.removeErrorListeners();
			parser.addErrorListener(new VerboseListener(true));
		
			parser.setBuildParseTree(true);
	        
	        ParseTree tree = parser.program();
	        
	        int lexicalErrors = ((VerboseListener) (lexer.getErrorListeners().get(0))).getNumberErrors();
	        int syntaxErrors = ((VerboseListener) (parser.getErrorListeners().get(0))).getNumberErrors();
	        
	        AssetLanVisitorImpl visitor = new AssetLanVisitorImpl();
	       
	        Node ast = visitor.visit(tree);
	        
	        //Check number errors 
			if (lexicalErrors > 0 || syntaxErrors > 0) {
				System.out.println("The program was not in the right format. Exiting the compilation process now");
			} 
			else {
				STEnvironment env = new STEnvironment();
				ArrayList<SemanticError> err = ast.checkSemantics(env);
				if(err.size() > 0) {
					System.out.println("You had: " +err.size()+" errors:");
					for(SemanticError e : err)
						System.out.println("\t" + e);
				} 
				else {
					System.out.println("Visualizing AST...");
					System.out.println(ast.toPrint(""));
					
					Type programType = ast.typeCheck();
					if (!(programType instanceof VoidType)) {
						System.out.println("ERRORE nel TypeCheck");
					}
					else {
						System.out.println("Il programma è ben tipato");
						System.out.println("TEST");
						EEnvironment eenv = new EEnvironment();
						ast.analizeEffect(eenv);
						System.out.println("TEST");
					}
				}
			}
	  }
	
}
