package ast;

import java.util.ArrayList;

import util.Environment;
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
		
		String s="";
		
		for(FieldNode f : field) {
			s += f.toPrint(indent);
		}
		for(AssetNode a : asset) {
			s += a.toPrint(indent);
		}
		for(FunNode f : function) {
			s += f.toPrint(indent);
		}
		s += "\n" + initcall.toPrint(indent);
		
		return s;
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
		
		env.entryScope();
		
		for(FieldNode node : field)
            errors.addAll(node.checkSemantics(env));
		for(AssetNode node : asset)
            errors.addAll(node.checkSemantics(env));
		for(FunNode node : function)
            errors.addAll(node.checkSemantics(env));
        errors.addAll(initcall.checkSemantics(env));
        
        env.exitScope();
		return errors;
	}

}
