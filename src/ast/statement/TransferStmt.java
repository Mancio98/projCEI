package ast.statement;

import java.util.ArrayList;
import ast.Node;
import ast.exp.Exp;
import ast.exp.IdExp;
import util.SemanticError;
import util.Environment;

public class TransferStmt extends Statement {

	private final IdExp id;

	public TransferStmt(IdExp id) {
		this.id = id;
	}

	@Override
	public String toPrint(String indent) {
		return indent + "Transfer:\n" + indent + "\tLeft: \n" + this.id.toPrint(indent + "\t\t") ;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		errors.addAll(id.checkSemantics(env));
		return errors;
	}

	@Override
	public Node typeCheck() {
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}

}
