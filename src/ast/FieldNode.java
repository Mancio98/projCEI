package ast;

import java.util.ArrayList;

import ast.exp.Exp;
import util.Environment;
import util.SemanticError;

//Used for a declaration of an ID and in case also for the assignment of it
public class FieldNode extends VarNode {
	
	private Exp exp;

	public FieldNode(int row,int column,String id, Node type, Exp exp) {
		super(row, column, id, type);
		this.exp = exp;
	}
	
	public FieldNode(int row,int column,String id, Node type) {
		super(row, column, id, type);
		this.exp = null;
	}

	public Node getExp() {
		return this.exp;
	}
	
	@Override
	public String toPrint(String indent) {
		String s = super.toPrint(indent);
		
		return indent + "Field:\n" + indent + "\t" + s +(exp!=null?  "\n" + indent
				+ "\t =: " + this.exp.toPrint(indent + "\t\t"): "") + "\n"; 
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

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = super.checkSemantics(env);
		if(exp != null)
			errors.addAll(exp.checkSemantics(env));
		return errors;
	}

}

