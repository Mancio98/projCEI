package ast;

import java.util.ArrayList;

import ast.exp.Exp;
import util.Environment;
import util.Environment.UndeclaredIdException;
import util.SemanticError;
import ast.type.Type;

// DA MODIFICARE COME CallNode

////Used to the management of initial function's call
public class InitcallNode extends Node {
	private String id;
	private ArrayList<Exp> exp1;
	private ArrayList<Exp> exp2;

	public InitcallNode(int row, int column, String id, ArrayList<Exp> exp1, ArrayList<Exp> exp2) {
		super(row, column);
		this.id = id;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	/*
	@Override
	public String toPrint(String indent) {
		String s = indent + "Initcall:\n" + indent + "\tFun: " + this.id ;
		s += " (";
		int i=0;
		for(Exp e : exp1) {
			if(i==0) {
				i++;
				s += "\n" + e.toPrintInFun(" ");
			}
			else {
				s += "\n" + e.toPrintInFun(", ");
			}
		}
		s+= " )\n[\n";
		i=0;
		for(Exp e : exp2) {
			if (i==0){
				i++;
				s += "\n" + e.toPrintInFun(" ");
			}
			else {
				s += "\n" + e.toPrintInFun(", ");
			}
		}
		s+= " ]";
		return s;
	}
	*/
	
	@Override
	public String toPrint(String indent) {
		String s = indent + "Initcall:\n" + indent + "\tFun: " + this.id ;
		s += "\n" + indent + "\t(";
		int i = 0;
		for(Exp e : this.exp1) {
			if(i == 0) {
				i++;
				s += "\n" + e.toPrint(indent + "\t\t");
			}
			else {
				s += "\n" + e.toPrint(indent + "\t\t");
			}
			s += " ,";
		}
		
		if (i == 0) {
			s += " )\n" + indent + "\t[";
		}
		else {
			s = s.substring(0, s.length() - 2);
			s += "\n" + indent + " \t)\n" + indent + "\t[";
		}
		
		i = 0;		
		for(Exp e : this.exp2) {
			if (i == 0) {
				i++;
				s += "\n" + e.toPrint(indent + "\t\t");
			}
			else {
				s += "\n" + e.toPrint(indent + "\t\t");
			}
			s += " ,";
		}
		
		
		if (i == 0) {
			s += " ]";
		}
		else {
			s = s.substring(0, s.length() - 2);
			s += "\n" + indent + "\t]";
		}
		return s;
	}

	@Override
	public Type typeCheck() {
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
			errors.add(new SemanticError(super.row, super.column, " " + id +" undeclared"));
		}
			
		for(Exp nodeExp : this.exp1) {
			errors.addAll(nodeExp.checkSemantics(env));
		}			
		
		for(Exp nodeExp : this.exp2) {
			errors.addAll(nodeExp.checkSemantics(env));
		}
			
		
		
		return errors;
	}

}
