package ast;

import java.util.ArrayList;

import ast.exp.Exp;
import util.Environment;
import util.Environment.UndeclaredIdException;
import util.SemanticError;

////Used to the management of initial function's call
public class InitcallNode extends Node {
	private String id;
	private ArrayList<Exp> exp1;
	private ArrayList<Exp> exp2;

	public InitcallNode(int row,int column,String id, ArrayList<Exp> exp1, ArrayList<Exp> exp2) {
		super(row, column);
		this.id = id;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}

	@Override
	public String toPrint(String indent) {
		String s = indent + "Initcall:\n" + indent + "\t Fun: " + id ;
		s+=" (";
		int i=0;
		for(Exp e : exp1) {
			if(i==0) {
				i++;
				s = s + e.toPrint(" ");
			}
			else {
				s = s + e.toPrint(", ");
			}
		}
		s+= " ) [";
		i=0;
		for(Exp t : exp2) {
			if (i==0){
				i++;
				s +=  t.toPrint(" ");
			}
			else {
				s += t.toPrint(", ");
			}
		}
		s+= " ]";
		return s;
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
		}
		catch (UndeclaredIdException e) {
			errors.add(new SemanticError(super.row + ":" + super.column + " " + id +" undeclared"));
		}
			
		for(Exp nodeExp : exp1) {
			errors.addAll(nodeExp.checkSemantics(env));
		}			
		
		for(Exp nodeExp : exp2) {
			errors.addAll(nodeExp.checkSemantics(env));
		}
			
		
		
		return errors;
	}

}
