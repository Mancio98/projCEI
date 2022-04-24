package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import ast.statement.Statement;
import ast.exp.Exp;
import util.Environment;
import util.Environment.UndeclaredIdException;
import util.SemanticError;

//Used to the management of function's call
public class CallNode extends Statement{
	
	private String id;
	private List<Exp> exp;
	private List<IdNode> idList;

	public CallNode(int row, int column,String id,ArrayList<IdNode> i, ArrayList<Exp> e) {
		super(row, column);
		this.id=id;
		exp=e;
		idList=i;
	}

	@Override
	public String toPrint(String indent) {
		String s = indent + "Call:\n" + indent + "\t Fun: " + id ;
		s+=" (";
		int i=0;
		for(Exp e : exp) {
			if(i==0) {
				i++;
				s = s + e.toPrint(" ");
			}
			else {
				s = s + e.toPrint(", ");
			}
		}
		s+= " ) [";
		i=0;
		for(IdNode t : idList) {
			if (i==0){
				i++;
				s += t.toPrint(" ");
			}
			else {
				s += t.toPrint(", ");
			}
		}
		s+= " ]";
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
		
		try {
			
			env.lookUp(this.id);
			
			}
		catch (UndeclaredIdException e) {
			errors.add(new SemanticError(super.row + ":" + super.column + " " + id+" undeclared"));
		}
		
		for(Exp nodeExp : exp) {
				
			errors.addAll(nodeExp.checkSemantics(env));
		}
			
		for(IdNode nodeId : idList) {
			errors.addAll(nodeId.checkSemantics(env));
		}
		
		
		return errors;
	}

}
