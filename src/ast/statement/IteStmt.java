package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.exp.Exp;
import util.SemanticError;
import util.Environment;
import ast.type.Type;
import ast.type.BoolType;
import util.TypeError;

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
        return indent + "If:\n" + this.exp.toPrint(indent + "\t") + "\n" + indent + "Then:\n"
                + this.thenStmt.toPrint(indent + "\t") 
                + (this.elseStmt != null ? "\n" + indent + "Else:\n" + this.elseStmt.toPrint(indent+ "\t") : "");
    }
		
	@Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        errors.addAll(exp.checkSemantics(env));
        errors.addAll(thenStmt.checkSemantics(env));
        if (this.elseStmt != null) {
            errors.addAll(this.elseStmt.checkSemantics(env));
        }
        return errors;
    }

	@Override
	public Type typeCheck() {
		Type expType = this.exp.typeCheck();
        if (!(expType instanceof BoolType)) {
            new TypeError(super.row, super.column,
                    "If condition must be of [" + (new BoolType()).getType() + "]");
        }

        Type thenType = this.thenStmt.typeCheck();

        if (this.elseStmt == null)
            return thenType; // return null;

        Type elseType = this.elseStmt.typeCheck();

        if (elseType == null || thenType == null)
            return null;
        
        if (elseType.equals(thenType))
            return thenType;

        new TypeError(super.row, super.column, "Braches types mismatch");
        
        return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

}
