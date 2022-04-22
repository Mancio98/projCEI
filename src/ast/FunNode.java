package ast;

import java.util.ArrayList;

import util.Environment;
import util.Environment.DuplicateEntryException;
import util.SemanticError;

public class FunNode implements Node{
	private String id;
	private Node type;
	private DecNode parDec;
	private AdecNode parAdec;
	private ArrayList<DecNode> decList;
	private ArrayList<Node> statementList;

	public FunNode(String i, Node t) {
		id=i;
		type=t;
	}
	
	public void addPar(DecNode d,AdecNode a) {
		
		if(d == null)
			parDec = new DecNode(new ArrayList<VarNode>());
		else
			parDec=d;
		
		if(a == null)
			parAdec = new AdecNode(new ArrayList<AssetNode>());
		else
			parAdec=a;
	}
	
	public void addBody(ArrayList<DecNode> d,ArrayList<Node> s) {
		
		decList=d;
		statementList=s;
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
		
		 ArrayList<SemanticError> semErrors = new  ArrayList<SemanticError>();
		 
		try {
			
			
			ArrayList<Node> parTypes = new ArrayList<Node>();
						
			for(VarNode n : parDec.getListDec()) {
				
				parTypes.add(n.getType());
			}
			
			ArrayList<Node> parATypes = new ArrayList<Node>();
			
			for(AssetNode n : parAdec.getListAdec()) {
				
				parATypes.add(new AssetTypeNode());
			}
			
			env.addDeclaration(this.id, new FunTypeNode(parTypes, parATypes, this.type));
			
			env.entryScope();
			
			for(VarNode n : parDec.getListDec()) {
				
				env.addDeclaration(n.getId(), n.getType());
			}
		
			for(AssetNode n : parAdec.getListAdec()) {
				
				env.addDeclaration(n.getID(), new AssetTypeNode());
			}
			
			
			for(DecNode n : decList) {
				
				for(VarNode ni : n.getListDec())
					env.addDeclaration(ni.getId(), ni.getType());
			}
			
			for(Node n : statementList) {
				
				semErrors.addAll(n.checkSemantics(env));
			}
			
		
		} catch (DuplicateEntryException e) {
			semErrors.add(new SemanticError("Fun id "+id+" already declared"));
			e.printStackTrace();
		}
		
		
		
		return semErrors;
	}
	
	
}