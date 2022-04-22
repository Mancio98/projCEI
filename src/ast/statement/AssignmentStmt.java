package ast.statement;

import java.util.ArrayList;
import ast.Node;
import ast.exp.Exp;
import ast.exp.IdExp;
import util.SemanticError;
import util.Environment;

public class AssignmentStmt extends Statement {

	private final IdExp left;
	//private final String id;
	private final Exp exp;

	
	public AssignmentStmt(IdExp left, Exp exp) {
		this.left = left;
		this.exp = exp;
	}
	
	
	/*
	public AssignmentStmt(String id, Exp exp) {
		this.id = id;
		this.exp = exp;
	}*/
	
	@Override
	public String toPrint(String indent) {
		return indent + "Assignment:\n" + indent + "\tLeft: \n" + this.left.toPrint(indent + "\t\t") + indent
				+ "\tRight: \n" + this.exp.toPrint(indent + "\t\t");
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
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}

}
