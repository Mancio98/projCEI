package MainPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class VerboseListener extends BaseErrorListener { 
	
	private int lexicalerrors = 0;
	private FileWriter lexerrors;
	
	public VerboseListener() throws IOException {
		super();
		lexerrors = new FileWriter("src/lexicalerrors.txt");
		lexerrors.write("");
				
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
	int line, int charPositionInLine, String msg,
	RecognitionException e){
		
		/*
		List<String> stack = ((Parser)recognizer).getRuleInvocationStack(); 
		Collections.reverse(stack);
		System.err.println("rule stack: "+stack);
		*/
		String errore = "line "+line+":"+charPositionInLine+": "+msg+"\n"+e+"\n";
		
		try {

		      lexerrors.append(errore);

		      System.out.println("Successfully wrote to the file.");
	    } catch (IOException err) {
	      System.out.println("An error occurred.");
	      err.printStackTrace();
	    }
		
		System.err.println(errore);
		
		lexicalerrors ++;
		
		/*
		CommonTokenStream tokens = (CommonTokenStream)recognizer.getInputStream();
		String input = tokens.getTokenSource().getInputStream().toString();
		System.out.println(input);
		*/
		
	}
	
	public int getNumberErrors() throws IOException {
		lexerrors.close();
		return this.lexicalerrors;
	}
}
