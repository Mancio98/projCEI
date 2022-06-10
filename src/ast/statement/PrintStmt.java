package ast.statement;

import java.util.ArrayList;
import ast.type.Type;
import ast.type.VoidType;
import ast.exp.Exp;
import util.SemanticError;
import util.EEnvironment;
import util.Environment;
import util.STEnvironment;

//Used for rule like "print exp"
public class PrintStmt extends Statement {

	private final Exp exp;

	public PrintStmt(int row,int column,Exp exp) {
		super(row, column);
		this.exp = exp;
	}

	@Override
	public String toPrint(String indent) {
		return indent + "Print:\n" + this.exp.toPrint(indent + "\t");
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		errors.addAll(this.exp.checkSemantics(env));
		return errors;
	}

	@Override
	public Type typeCheck() {
		this.exp.typeCheck();
		//VEDERE SE VA FATTO CONTROLLO INT OR BOOL
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void analizeEffect(EEnvironment env) {
		return ;
	}

}
