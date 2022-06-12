package ast;

import java.util.ArrayList;

import util.EEntryAsset;
import util.EEntryFun;
import util.EEnvironment;
import util.STEnvironment;
import util.SemanticError;
import ast.dec.FieldNode;
import ast.dec.AssetNode;
import ast.dec.FunNode;
import ast.statement.CallStmt;
import ast.statement.IteStmt;
import ast.type.FunType;
import ast.type.Type;
import ast.type.VoidType;

//Used as the entry point of the program
public class ProgramNode extends Node {
	
	private ArrayList<FieldNode> field;
	private ArrayList<AssetNode> asset;
	private ArrayList<FunNode> function;
	private InitcallNode initcall;
	
	// VEDERE COME FARE
	public AssetNode globalAsset;
	
	
	public ProgramNode(int row,int column,ArrayList<FieldNode> field, ArrayList<AssetNode> asset, ArrayList<FunNode> fun, InitcallNode init) {
		super(row, column);
		this.field = field;
		this.asset = asset;
		this.function = fun;
		this.initcall = init;
	}

	@Override
	public String toPrint(String indent) {
		
		String s = "";
		
		for(FieldNode f : this.field) {
			s += "\n" + f.toPrint(indent);
		}
		for(AssetNode a : this.asset) {
			s += "\n" + a.toPrint(indent);
		}
		for(FunNode f : this.function) {
			s += "\n" + f.toPrint(indent);
		}
		s += "\n" + this.initcall.toPrint(indent);
		
		return s;
	}


	@Override
	public Type typeCheck() {
		for(FieldNode f : this.field) {
			System.out.println("D");
			f.typeCheck();
		}
		for(AssetNode a : this.asset) {
			System.out.println("A");
			a.typeCheck();
		}
		for(FunNode f : this.function) {
			System.out.println("F");
			f.typeCheck();
		}
		System.out.println("P");
		Type programType = this.initcall.typeCheck();
		return programType;
	}


	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		
		env.entryScope();
		
		for(FieldNode node : this.field)
            errors.addAll(node.checkSemantics(env));
		for(AssetNode node : this.asset)
            errors.addAll(node.checkSemantics(env));
		for(FunNode node : this.function)
            errors.addAll(node.checkSemantics(env));
        errors.addAll(this.initcall.checkSemantics(env));
        
        env.exitScope();
		return errors;
	}

	// FARE I CONTROLLI SULLA LIQUIDITY
	@Override
	public void analizeEffect(EEnvironment env) {
		env.entryScope();
		
		for(FieldNode f : this.field) {
			// VEDERE SE SI PU� FARE MEGLIO
			f.analizeEffect(env);
		}
		for(AssetNode a : this.asset) {
			a.analizeEffect(env);
		}
		for(FunNode f : this.function) {
			f.analizeEffect(env);
		}
		
		EEnvironment envLiq = env.clone();
		for(FunNode f : this.function) {
			((EEntryFun)(envLiq.lookUp(f.getId()))).setFunNode(f);
		}
		this.initcall.analizeLiquidity(envLiq);
		this.initcall.analizeEffect(env);
		
		env.exitScope();
		
		return ;
	}
}
