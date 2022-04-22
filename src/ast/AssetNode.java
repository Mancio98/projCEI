package ast;

import java.util.ArrayList;



import util.Environment;
import util.SemanticError;
import util.Type;

public class AssetNode implements Node{
	
	private String id;
	
	public AssetNode(String id) {
		id=id;
	}

	public String getID() {
		return id;
	}
	
	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return null;
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
		
		
		return null;
	}

}
