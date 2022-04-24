package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import ast.exp.Exp;
import util.SemanticError;
import util.Environment;

//Used for rule like "ID = exp"
public class AssignmentStmt extends Statement {

	private final IdNode left;
	//private final String id;
	private final Exp exp;

	
	public AssignmentStmt(int row,int column,IdNode left, Exp exp) {
		super(row, column);
		this.left = left;
		this.exp = exp;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + "Assignment:\n" + indent + "\tLeft: \n" + this.left.toPrint(indent + "\t\t") + "\n" + indent
				+ "\tRight: \n" + this.exp.toPrint(indent + "\t\t") + "\n";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		errors.addAll(left.checkSemantics(env));
		errors.addAll(exp.checkSemantics(env));
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
