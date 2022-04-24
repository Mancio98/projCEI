package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

//Used to specify the type of a node
public class AssetTypeNode extends Node {

	public AssetTypeNode(int row, int column) {
		super(row, column);
	}

	@Override
	public String toPrint(String indent) {
		return indent + "Type: Asset";
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
		// TODO Auto-generated method stub
		return null;
	}

}
