package ast.statement;

import java.util.ArrayList;

import ast.Node;
import ast.exp.Exp;
import ast.exp.IdExp;
import util.SemanticError;
import util.Environment;

public class IteStmt extends Statement {

	private final Exp exp;
	private final Statement thenStmt, elseStmt;

	public IteStmt(Exp exp, Statement thenStmt) {
		this.exp = exp;
		this.thenStmt = thenStmt;
		this.elseStmt = null;
	}
	
	public IteStmt(Exp exp, Statement thenStmt, Statement elseStmt) {
		this.exp = exp;
		this.thenStmt = thenStmt;
		this.elseStmt = elseStmt;
	}

	@Override
    public String toPrint(String indent) {
        return indent + "\tIf:\n" + exp.toPrint(indent + "\t") + "\n" + indent + "\tThen:\n"
                + thenStmt.toPrint(indent + "\t") + "\n"
                + (elseStmt != null ? indent + "\tElse:\n" + elseStmt.toPrint(indent) : "");
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
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}

}
