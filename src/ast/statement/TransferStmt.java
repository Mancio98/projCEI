package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import ast.exp.Exp;
import util.SemanticError;
import util.Environment;

//Used for rule like "transfer ID"
public class TransferStmt extends Statement {

	private final IdNode id;

	public TransferStmt(int row,int column,IdNode id) {
		super(row, column);
		this.id = id;
	}

	@Override
	public String toPrint(String indent) {
		return indent + "Transfer:\n" + this.id.toPrint(indent + "\t") +"\n";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		errors.addAll(id.checkSemantics(env));
		return errors;
	}

	@Override
	public Node typeCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

}
