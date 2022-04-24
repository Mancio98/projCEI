package ast;

import java.util.ArrayList;

import ast.exp.Exp;
import util.Environment;
import util.Environment.DuplicateEntryException;
import util.SemanticError;
import ast.statement.Statement;

//Used to the management of function's declaration
public class FunNode extends Node{
	private String id;
	private Node type;
	private DecNode parDec;
	private AdecNode parAdec;
	private ArrayList<DecNode> decList;
	private ArrayList<Statement> statementList;

	public FunNode(int row,int column,String i, Node t) {
		super(row, column);
		id=i;
		type=t;
	}
	
	public void addPar(DecNode d,AdecNode a) {
		
		if(d == null)
			parDec = new DecNode(-1,-1,new ArrayList<VarNode>());
		else
			parDec=d;
		
		if(a == null)
			parAdec = new AdecNode(-1,-1,new ArrayList<AssetNode>());
		else
			parAdec=a;
	}
	
	public void addBody(ArrayList<DecNode> d,ArrayList<Statement> s) {
		decList=d;
		statementList=s;
	}
	

	@Override
	public String toPrint(String indent) {
		String s = indent + "Function :\n" + indent + "\t Fun: " + id + " " + type.toPrint(indent);
		s += parDec.toPrint(indent +"\t\t");
		s += parAdec.toPrint(indent +"\t\t");
		
		for(DecNode d : decList) {
			s += d.toPrint(indent+"\t\t\t");
		}
		
		for(Statement d : statementList) {
			s += d.toPrint(indent+"\t\t\t");
		}
		
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
		
		ArrayList<SemanticError> semErrors = new  ArrayList<SemanticError>();
							
		ArrayList<Node> parTypes = new ArrayList<Node>();
						
		for(VarNode n : parDec.getListDec()) {
				
			parTypes.add(n.getType());
		}
			
		ArrayList<Node> parATypes = new ArrayList<Node>();
			
		for(AssetNode n : parAdec.getListAdec()) {
		
			parATypes.add(new AssetTypeNode(-1,-1));
		}
		
		try {

			env.addDeclaration(this.id, new FunTypeNode(-1,-1,parTypes, parATypes, this.type));
			
		} catch (DuplicateEntryException e) {
		
			semErrors.add(new SemanticError(super.row + ":" + super.column + " id "+ id +" already declared"));
		
		}
			
		env.entryScope();
			
		for(VarNode n : parDec.getListDec()) {
				
			semErrors.addAll(n.checkSemantics(env));
								
		}
		
		for(AssetNode n : parAdec.getListAdec()) {
				
			semErrors.addAll(n.checkSemantics(env));
				
		}
			
		for(DecNode n : decList) {
					
				semErrors.addAll(n.checkSemantics(env));
					
		}
		
		for(Statement n : statementList) {
				
			semErrors.addAll(n.checkSemantics(env));

		}
			
		env.exitScope();
		
		return semErrors;
	}
	
	
}