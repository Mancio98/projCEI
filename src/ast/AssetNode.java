package ast;

import java.util.ArrayList;



import util.Environment;
import util.Environment.DuplicateEntryException;
import util.SemanticError;

//Used to declaration of variable of type asset
public class AssetNode extends Node{
	
	private String id;
	
	public AssetNode(int row,int column,String id) {
		super(row, column);
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	@Override
	public String toPrint(String indent) {
		AssetTypeNode asset=new AssetTypeNode(-1,-1);
		return indent + "Var: " + id + asset.toPrint(" ") +"\n" ;
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
			env.addDeclaration(this.id, new AssetTypeNode(-1,-1));
		}
		catch (DuplicateEntryException e) {
			errors.add(new SemanticError(super.row + ":" + super.column + " asset id "+ id +" already declared"));
			e.printStackTrace();
		}
		
		return errors;
	}

}
