package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class ProgramNode implements Node {
	
	private ArrayList<FieldNode> field;
	private ArrayList<AssetNode> asset;
	private ArrayList<FunNode> function;
	private InitcallNode initcall;
	
	
	public ProgramNode(ArrayList<FieldNode> field, ArrayList<AssetNode> asset, ArrayList<FunNode> fun, InitcallNode init) {
		this.field = field;
		this.asset = asset;
		this.function = fun;
		this.initcall = init;
	}
	/*
	
	public void addField(ArrayList<FieldNode> field) {
		this.field = field;
	}
	
	public void addAsset(ArrayList<AssetNode> asset) {
		this.asset = asset;
	}
	
	public void addFunction(ArrayList<FunNode> fun) {
		this.function = fun;
	}*/


	@Override
	public String toPrint(String indent) {
		
		
		
		for(FieldNode f : field) {
			indent += f.toPrint("Field:\n");
		}
		return indent;
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
		
		env.entryScope();
		
		for(FieldNode node : field)
            errors.addAll(node.checkSemantics(env));
		for(AssetNode node : asset)
            errors.addAll(node.checkSemantics(env));
		for(FunNode node : function)
            errors.addAll(node.checkSemantics(env));
        errors.addAll(initcall.checkSemantics(env));
        
        env.exitScope();
		return errors;
	}

}
