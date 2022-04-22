package ast;

import java.util.ArrayList;

import ast.exp.Exp;
import util.Environment;
import util.Environment.UndeclaredIdException;
import util.SemanticError;

public class InitcallNode implements Node {
	private String id;
	private ArrayList<Exp> exp1;
	private ArrayList<Exp> exp2;

	public InitcallNode(String id, ArrayList<Exp> exp1, ArrayList<Exp> exp2) {
		this.id = id;
		this.exp1 = exp1;
		this.exp2 = exp2;
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
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		
		try {
			
			env.lookUp(this.id);
			
			for(Exp nodeExp : exp1) {
				errors.addAll(nodeExp.checkSemantics(env));
			}
			
			for(Exp nodeExp : exp2) {
				errors.addAll(nodeExp.checkSemantics(env));
			}
			
		} catch (UndeclaredIdException e) {
			
			errors.add(new SemanticError(id + " undeclared\n"));
			e.printStackTrace();
		}
		
		return errors;
	}

}
