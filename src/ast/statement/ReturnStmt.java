package ast.statement;

import java.util.ArrayList;
import ast.Node;
import ast.exp.Exp;
import util.SemanticError;
import util.Environment;

//Used for rule like "return (exp)?"
public class ReturnStmt extends Statement {

	private final Exp exp;

	public ReturnStmt(int row,int column) {
		super(row, column);
		this.exp = null;
	}
	
	public ReturnStmt(int row,int column,Exp exp) {
		super(row, column);
		this.exp = exp;
	}

	@Override
    public String toPrint(String indent) {
        return indent + "Return\n" + (exp != null ? exp.toPrint(indent + "\t") : "") + "\n";
    }

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		if (exp != null)
            errors.addAll(exp.checkSemantics(env));
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
