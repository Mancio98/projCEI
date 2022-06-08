package ast.dec;

import java.util.ArrayList;

import util.AssetLanlib;
import util.Environment;
import util.Environment.DuplicateEntryException;
import util.EnvironmentAsset;
import util.SemanticError;
import ast.type.BoolType;
import ast.type.Type;
import ast.type.VoidType;
import ast.Node;

//Used to declaration of variable of type bool or int
public class VarNode extends Node {
	
	protected Type type;
	protected String id;
	
	public VarNode(int row, int column, Type type, String id) {
		super(row, column);
		this.type = type;
		this.id = id;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public String getId() {
		return this.id;
	}

	@Override
	public String toPrint(String indent) {
		return indent + "Var: " + this.id + " " + this.type.toPrint("");
	}

	@Override
	public Type typeCheck() {
	    return new VoidType();
	}

	@Override
	public String codeGeneration() {
		
		
		if(this.type.isSubtype(new BoolType()))
			return "addi sp sp -1\n";
		else
			return "addi sp sp -4\n";
		
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		
		try {
			env.addDeclaration(this.id, this.type);
		}
		catch (DuplicateEntryException e) {
			errors.add(new SemanticError(super.row, super.column, " var "+ this.id +" already declared"));
		}
		
		return errors;
	}

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}

}
