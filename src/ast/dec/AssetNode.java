package ast.dec;

import java.util.ArrayList;

import util.EEnvironment;
import util.STEnvironment;
import util.STEnvironment.DuplicateEntryException;
import util.SemanticError;
import ast.Node;
import ast.type.Type;
import ast.type.AssetType;
import ast.type.VoidType;

//Used to declaration of variable of type asset
public class AssetNode extends Node {
	
	private Type type;
	private String id;
	
	public AssetNode(int row, int column, String id) {
		super(row, column);
		this.type = new AssetType();
		this.id = id;
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
		
		return "addi $sp $sp -1\n";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		
		try {
			env.addDeclaration(this.id, this.type);
		}
		catch (DuplicateEntryException e) {
			errors.add(new SemanticError(super.row, super.column, "asset id "+ this.id +" already declared"));
		}
		
		return errors;
	}

	// Per ogni asset dichiaro, creato un'entry nella tabella con stato iniziale 0
	@Override
	public void analyzeEffect(EEnvironment env) {
		env.addDeclarationAsset(this.id, "0");
		return ;
	}

}
