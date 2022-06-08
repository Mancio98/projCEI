package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.exp.Exp;
import util.SemanticError;
import util.Environment;
import util.EnvironmentAsset;
import ast.type.Type;
import ast.type.VoidType;
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
            System.out.println(new TypeError(this.exp.getRow(), this.exp.getColumn(),
                    			"If condition must be of type [" + (new BoolType()).getType() + "] instead of type " + expType.getType()).toPrint());
            System.exit(0);
        }
        
        Type thenType = this.thenStmt.typeCheck();
        
        if (this.thenStmt instanceof CallStmt) {
			if (!thenType.isSubtype(new VoidType())) {
				System.out.println(new TypeError(this.thenStmt.getRow(), this.thenStmt.getColumn(),
									"Invalid call of function [" + ((CallStmt)this.thenStmt).getId() + "]").toPrint());
				System.exit(0);
			}
		}
        
        if (this.elseStmt != null) {
        	Type elseType = this.elseStmt.typeCheck();
            
        	if (this.elseStmt instanceof CallStmt) {
    			if (!elseType.isSubtype(new VoidType())) {
    				System.out.println(new TypeError(this.elseStmt.getRow(), this.elseStmt.getColumn(),
    									"Invalid call of function [" + ((CallStmt)this.elseStmt).getId() + "]").toPrint());
    				System.exit(0);
    			}
    		}
        	
            if (!elseType.isSubtype(thenType)) {
            	System.out.println(new TypeError(super.row, super.column, "Branches types mismatch").toPrint());
            	System.exit(0);
            }
            
            return thenType;
        }
        else if (this.thenStmt instanceof ReturnStmt) {
        	System.out.println(new TypeError(super.row, super.column, "Irregular").toPrint());
        	System.exit(0);
        }
        
        return new VoidType();
	}

	@Override
	public String codeGeneration() {
						
		
		return null;
	}

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}

}
