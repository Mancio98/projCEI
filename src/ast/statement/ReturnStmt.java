package ast.statement;

import java.util.ArrayList;
import ast.Node;
import ast.exp.Exp;
import util.SemanticError;
import util.Environment;

public class ReturnStmt extends Statement {

	private final Exp exp;

	public ReturnStmt() {
		this.exp = null;
	}
	
	public ReturnStmt(Exp exp) {
		this.exp = exp;
	}

	@Override
    public String toPrint(String indent) {
        return indent + "Stmt: return\n" + (exp != null ? exp.toPrint(indent) : "");
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
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}

}
