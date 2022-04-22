package ast;


import java.util.ArrayList;

import ast.exp.Exp;
import util.Environment;
import util.SemanticError;

public class FieldNode extends VarNode {
	
	private Exp exp;

	public FieldNode(String id, Node type, Exp exp) {
		super(id, type);
		this.exp = exp;
	}
	
	public FieldNode(String id, Node type) {
		super(id, type);
		this.exp = null;
	}

	public Node getExp() {
		return this.exp;
	}
	
	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return null;
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

