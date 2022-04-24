package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import ast.exp.Exp;
import util.SemanticError;
import util.Environment;

//Used for rule like "if ( exp ) statement (else statement)?"
public class IteStmt extends Statement {

	private final Exp exp;
	private final Statement thenStmt, elseStmt;

	public IteStmt(int row,int column,Exp exp, Statement thenStmt) {
		super(row, column);
		this.exp = exp;
		this.thenStmt = thenStmt;
		this.elseStmt = null;
	}
	
	public IteStmt(int row,int column,Exp exp, Statement thenStmt, Statement elseStmt) {
		super(row, column);
		this.exp = exp;
		this.thenStmt = thenStmt;
		this.elseStmt = elseStmt;
	}

	@Override
    public String toPrint(String indent) {
        return indent + "If:\n" + exp.toPrint(indent + "\t") + "\n" + indent + "Then:\n"
                + thenStmt.toPrint(indent + "\t") 
                + (elseStmt != null ? "\n" +indent + "Else:\n" + elseStmt.toPrint(indent+ "\t") : "");
    }
		
	@Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        errors.addAll(exp.checkSemantics(env));
        errors.addAll(thenStmt.checkSemantics(env));
        if (elseStmt != null) {
            errors.addAll(elseStmt.checkSemantics(env));
        }
        return errors;
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

}
