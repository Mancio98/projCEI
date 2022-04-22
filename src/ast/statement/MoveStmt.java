package ast.statement;

import java.util.ArrayList;
import ast.Node;
import ast.exp.Exp;
import ast.exp.IdExp;
import util.SemanticError;
import util.Environment;

public class MoveStmt extends Statement {

	private final IdExp left;
	private final IdExp right;

	public MoveStmt(IdExp left, IdExp right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toPrint(String indent) {
		return indent + "Move:\n" + indent + "\tLeft: \n" + this.left.toPrint(indent + "\t\t") + indent
				+ "\tRight: \n" + this.right.toPrint(indent + "\t\t");
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		errors.addAll(left.checkSemantics(env));
		errors.addAll(right.checkSemantics(env));
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
