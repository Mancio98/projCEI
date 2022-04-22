package ast;

import java.util.ArrayList;



import util.Environment;
import util.Environment.DuplicateEntryException;
import util.SemanticError;
import util.Type;

public class AssetNode implements Node{
	
	private String id;
	
	public AssetNode(String id) {
		this.id = id;
	}

	public String getId() {
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
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		
		try {
			env.addDeclaration(this.id, new AssetTypeNode());
		}
		catch (DuplicateEntryException e) {
			errors.add(new SemanticError("Asset id "+ id +" already declared"));
			e.printStackTrace();
		}
		
		return errors;
	}

}
