package ast.dec;

import java.util.ArrayList;

import util.EEnvironment;
import util.STEnvironment;
import util.SemanticError;
import ast.Node;
import ast.type.Type;
import ast.type.VoidType;

//Used to the management of declaration of multiple ID of type asset
public class AdecNode extends Node {
	
	private ArrayList<AssetNode> adec;

	public AdecNode(int row, int col, ArrayList<AssetNode> adec) {
		super(row, col);
		this.adec = adec;
	}
	
	public AdecNode(ArrayList<AssetNode> adec) {
		super(-1, -1);
		this.adec = adec;
	}

	public ArrayList<AssetNode> getListAdec(){
		return this.adec;
	}
	@Override
	public String toPrint(String indent) {
		String s="";
		for(AssetNode v: this.adec) {
			s += "\n";
			s += v.toPrint(indent);
		}
		return s;
	}

	@Override
	public Type typeCheck() {
		for (AssetNode node : adec) {
			node.typeCheck();
		}
		return new VoidType();
	}

	@Override
	public String codeGeneration() {
		String adeccgen = "";
		
		for(AssetNode node : adec)
			adeccgen += node.codeGeneration();
		return adeccgen;
	}
	
	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		
		for (AssetNode node : this.adec) {
			errors.addAll(node.checkSemantics(env));
		}
		
		return errors;
	}

	// For each asset list, we analyze effect of each asset
	@Override
	public void analyzeEffect(EEnvironment env) {
		for (AssetNode node : this.adec) {
			node.analyzeEffect(env);
		}
		return ;
	}

}
