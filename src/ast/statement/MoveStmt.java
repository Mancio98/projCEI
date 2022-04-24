package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import ast.exp.Exp;
import util.SemanticError;
import util.Environment;

//Used for rule like "ID -o ID"
public class MoveStmt extends Statement {

	private final IdNode left;
	private final IdNode right;

	public MoveStmt(int row,int column,IdNode left, IdNode right) {
		super(row, column);
		this.left = left;
		this.right = right;
	}

	@Override
	public String toPrint(String indent) {
		return indent + "Move:\n" + indent + "\tLeft: \n" + this.left.toPrint(indent + "\t\t") + "\n" + indent
				+ "\tRight: \n" + this.right.toPrint(indent + "\t\t") + "\n";
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

}
