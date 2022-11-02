package ast;

import java.util.ArrayList;

import ast.dec.AssetNode;
import ast.dec.FieldNode;
import ast.dec.FunNode;
import ast.type.Type;
import util.AssetLanlib;
import util.EEntryFun;
import util.EEnvironment;
import util.STEnvironment;
import util.SemanticError;

//Used as the entry point of the program
public class ProgramNode extends Node {
	
	private ArrayList<FieldNode> field;
	private ArrayList<AssetNode> asset;
	private ArrayList<FunNode> function;
	private InitcallNode initcall;
	
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
			f.typeCheck();
		}
		for(AssetNode a : this.asset) {
			a.typeCheck();
		}
		for(FunNode f : this.function) {
			f.typeCheck();
		}
		Type programType = this.initcall.typeCheck();
		return programType;
	}


	@Override
	public String codeGeneration() {
		
		String fieldcgen = "";
		String popdeclcgen ="";
		
		for(int i = this.field.size()-1; i>=0; i--) {
			fieldcgen += this.field.get(i).codeGeneration();
			popdeclcgen += "pop \n";
		}		
		String assetcgen = "";
		
		for(int i = this.asset.size()-1; i>=0; i--) {
			assetcgen += this.asset.get(i).codeGeneration();
			popdeclcgen += "pop \n";
		}
		
		String funcgen = "";
				
		for(FunNode node : this.function)
			funcgen += node.codeGeneration();
			
		String initcgen = this.initcall.codeGeneration();
				
		String progcgen = 	assetcgen+
							fieldcgen+
							funcgen+
							"move $fp $sp\n"+
							"li $t1 1\n"+
							"sub $fp $t1 $fp\n"+
							initcgen+
							popdeclcgen+
							"halt"+
							AssetLanlib.getCode();
		
		return progcgen;
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

	@Override
	public void analyzeEffect(EEnvironment env) {
		env.entryScope();
		
		// Analisi degli effetti delle varie dichiarazioni
		for(FieldNode f : this.field) {
			// VEDERE SE SI PUò FARE MEGLIO
			f.analyzeEffect(env);
		}
		for(AssetNode a : this.asset) {
			a.analyzeEffect(env);
		}
		// Analisi deglli effetti delle funzioni
		for(FunNode f : this.function) {
			f.analyzeEffect(env);
		}
		
		EEnvironment envLiq = env.clone();
		for(FunNode f : this.function) {
			((EEntryFun)(envLiq.lookUp(f.getId()))).setFunNode(f);
		}
		
		// Analisi della liquidit� delle sole funzioni che vengono chiamate durante l'esecuzione del programma
		this.initcall.analyzeLiquidity(envLiq);
		// Analisi della liquidit� del programma, ovvero degli asset globali del programma
		this.initcall.analyzeEffect(env);
		
		env.exitScope();
		
		return ;
	}
	
	

}
