package ast.dec;

import java.util.ArrayList;

import util.Environment;
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
		return new VoidType();
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		
		for (AssetNode node : adec) {
			errors.addAll(node.checkSemantics(env));
		}
		
		return errors;
	}

}
