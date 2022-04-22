package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import ast.statement.Statement;
import util.Environment;
import util.Environment.UndeclaredIdException;
import util.SemanticError;

public class CallNode extends Statement{
	
	private String id;
	private List<Node> exp;
	private List<String> idList;

	public CallNode(ArrayList<String> i, ArrayList<Node> e) {
		id=i.get(0);
		exp=e;
		i.remove(0);
		idList=i;
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
			
			env.lookUp(this.id);
			
			for(Node nodeExp : exp) {
				
				errors.addAll(nodeExp.checkSemantics(env));
			}
			
			for(String nodeId : idList) {
				
				env.lookUp(nodeId);
			}
			
		} catch (UndeclaredIdException e) {
			
			errors.add(new SemanticError(id+" undeclared\n"));
			e.printStackTrace();
		}
		
		return errors;
	}

}
