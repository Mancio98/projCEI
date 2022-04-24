package MainPackage;

import java.io.FileWriter;
import java.io.IOException;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

//Custom error listener used to write errors log to file
public class VerboseListener extends BaseErrorListener { 
	
	private int errors = 0;
	private FileWriter errorsLog;
	
	public VerboseListener(Boolean val) throws IOException {
		super();
		errorsLog = new FileWriter("src/errorsLog.txt",val);
		errorsLog.write("");
				
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
	int line, int charPositionInLine, String msg,
	RecognitionException e){

		String errore = "line "+line+":"+charPositionInLine+": "+msg+"\n\n";
		
		try {
		      errorsLog.append(errore);

		      System.out.println("Successfully wrote to the file.");
		      
	    } catch (IOException err) {
	      System.out.println("An error occurred.");
	      err.printStackTrace();
	    }
		
		errors++;
	}
	
	//Write error string on file and return found number errors
	public int getNumberErrors() throws IOException {
		errorsLog.close();
		return this.errors;
	}
}
