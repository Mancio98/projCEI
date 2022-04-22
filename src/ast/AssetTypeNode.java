package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class AssetTypeNode implements Node {

	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return indent+"AssetType\n";
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
