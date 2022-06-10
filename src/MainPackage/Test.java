package MainPackage;

import java.io.BufferedWriter;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;

import Interpreter.ExecuteVM;
import ast.Node;

import ast.type.Type;
import ast.type.VoidType;
import ast.AVMVisitorImpl;
import ast.AssetLanVisitorImpl;
import parser.AVMLexer;
import parser.AVMParser;
import parser.AssetLanLexer;
import parser.AssetLanParser;
import util.Environment;
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
				Environment env = new Environment();
				ArrayList<SemanticError> err = ast.checkSemantics(env);
				if(err.size() > 0) {
					System.out.println("You had: " +err.size()+" errors:");
					for(SemanticError e : err)
						System.out.println("\t" + e);
				} 
				else {
					System.out.println("Visualizing AST...");
					System.out.println(ast.toPrint(""));
					
					/*
					System.out.println("TEST");
					Type programType = ast.typeCheck();
					System.out.println("TEST");
					if (programType instanceof VoidType) {*/
						System.out.println("Il programma � ben tipato");
						
						String cgen = ast.codeGeneration();
						BufferedWriter out = new BufferedWriter(new FileWriter(fileName+".asm")); 
						out.write(cgen);
						out.close(); 
						System.out.println("Code generated! Assembling and running generated code.");

						
						CharStream inputASM = CharStreams.fromFileName(fileName+".asm");
						AVMLexer lexerASM = new AVMLexer(inputASM);
						CommonTokenStream tokensASM = new CommonTokenStream(lexerASM);
						AVMParser parserASM = new AVMParser(tokensASM);

						//parserASM.assembly();

						AVMVisitorImpl visitorAVM = new AVMVisitorImpl();
						visitorAVM.visit(parserASM.assembly());
						
						System.out.println("You had: "+lexerASM.lexicalErrors+" lexical errors and "+parserASM.getNumberOfSyntaxErrors()+" syntax errors.");
						if (lexerASM.lexicalErrors>0 || parserASM.getNumberOfSyntaxErrors()>0) System.exit(1);

						System.out.println("Starting Virtual Machine...");
						ExecuteVM vm = new ExecuteVM(visitorAVM.getCode());
						vm.cpu();
					/*}
					else {
						System.out.println("ERRORE nel TypeCheck");
					}*/
				}
			}
	  }
	
}
