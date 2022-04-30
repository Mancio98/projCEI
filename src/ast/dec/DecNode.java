package ast.dec;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;
import ast.Node;
import ast.type.Type;
import ast.type.VoidType;

//Used to the management of declaration of multiple ID of type int or bool
public class DecNode extends Node {
	
	private ArrayList<VarNode> dec;

	public DecNode(int row, int col, ArrayList<VarNode> dec) {
		super(row, col);
		this.dec = dec;
	}
	
	public DecNode(ArrayList<VarNode> dec) {
		super(-1, -1);
		this.dec = dec;
	}

	public ArrayList<VarNode> getListDec(){
		return this.dec;
	}

	@Override
	public String toPrint(String indent) {
		String s = "";
		for(VarNode v: this.dec) {
			s += "\n" + v.toPrint(indent);
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
		
		for (VarNode node : this.dec) {
			errors.addAll(node.checkSemantics(env));
		}
		
		return errors;
	}

}
