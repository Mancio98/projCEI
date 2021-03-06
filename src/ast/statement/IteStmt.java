package ast.statement;

import java.util.ArrayList;

import ast.exp.Exp;
import util.SemanticError;
import util.AssetLanlib;
import util.EEnvironment;
import util.STEnvironment;
import ast.type.Type;
import ast.type.VoidType;
import ast.type.BoolType;
import util.TypeError;

//Used for rule like "if ( exp ) statement (else statement)?"
public class IteStmt extends Statement {

	private final Exp exp;
	private ArrayList<Statement> thenStmtList, elseStmtList;

	/*public IteStmt(int row,int column,Exp exp, Statement thenStmt) {
		super(row, column);
		this.exp = exp;
		this.thenStmt = thenStmt;
		this.elseStmt = null;
	}*/
	
	public IteStmt(int row,int column,Exp exp, ArrayList<Statement> thenStmt, ArrayList<Statement> elseStmt) {
		super(row, column);
		this.exp = exp;
		this.thenStmtList = thenStmt;
		this.elseStmtList = elseStmt;
	}

	public ArrayList<Statement> getThenStmt() {
		return this.thenStmtList;
	}
	
	public ArrayList<Statement> getElseStmt() {
		return this.elseStmtList;
	}
	
	@Override
    public String toPrint(String indent) {
		String s = indent + "If:\n" + this.exp.toPrint(indent + "\t") + "\n" + indent + "Then:";
		
		for(Statement stmt : this.thenStmtList) {
			s += "\n" + stmt.toPrint(indent + "\t");
		}
		
		if (this.elseStmtList.size() > 0) {
			s += "\n" + indent + "Else:";
			
			for(Statement stmt : this.elseStmtList) {
				s += "\n" + stmt.toPrint(indent + "\t");
			}
		}
		
		return s;
        /*return indent + "If:\n" + this.exp.toPrint(indent + "\t") + "\n" + indent + "Then:\n"
                + this.thenStmtList.toPrint(indent + "\t") 
                + (this.elseStmtList != null ? "\n" + indent + "Else:\n" + this.elseStmtList.toPrint(indent+ "\t") : "");
        */
    }
		
	@Override
    public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        errors.addAll(exp.checkSemantics(env));
        
        for(Statement stmt : this.thenStmtList) {
        	errors.addAll(stmt.checkSemantics(env));
		}
        
        for(Statement stmt : this.elseStmtList) {
        	errors.addAll(stmt.checkSemantics(env));
		}
        
        return errors;
    }

	@Override
	public Type typeCheck() {
		Type expType = this.exp.typeCheck();
        if (!(expType instanceof BoolType)) {
            System.out.println(new TypeError(this.exp.getRow(), this.exp.getColumn(),
                    			"If condition must be of type [" + (new BoolType()).getType() + "] instead of type [" + expType.getType() + "]").toPrint());
            System.exit(0);
        }
        
        Type thenType = null;
        for(Statement stmt : this.thenStmtList) {
			Type stmtType = stmt.typeCheck();
			
			if (stmtType != null) {
				if (stmt instanceof CallStmt) {
	    			if (!stmtType.isSubtype(new VoidType())) {
	    				System.out.println(new TypeError(stmt.getRow(), stmt.getColumn(),
	    									"Invalid call of function [" + ((CallStmt)stmt).getId() + "]").toPrint());
	    				System.exit(0);
	    			}
	    		}
				else {
					if (this.thenStmtList.indexOf(stmt) != (this.thenStmtList.size() - 1)) {
						System.out.println(new TypeError(super.row, super.column, 
								"Return is not the last statement of the function").toPrint());
						System.exit(0);
					}
					else {
						thenType = stmtType;
					}
				}
			}
		}
        
        Type elseType = null;
        if (this.elseStmtList.size() > 0) {
        	for(Statement stmt : this.elseStmtList) {
    			Type stmtType = stmt.typeCheck();
    			
    			if (stmtType != null) {
    				if (stmt instanceof CallStmt) {
    	    			if (!stmtType.isSubtype(new VoidType())) {
    	    				System.out.println(new TypeError(stmt.getRow(), stmt.getColumn(),
    	    									"Invalid call of function [" + ((CallStmt)stmt).getId() + "]").toPrint());
    	    				System.exit(0);
    	    			}
    	    		}
    				else {
    					if (this.elseStmtList.indexOf(stmt) != (this.elseStmtList.size() - 1)) {
    						System.out.println(new TypeError(super.row, super.column, 
    								"Return is not the last statement of the function").toPrint());
    						System.exit(0);
    					}
    					else {
    						elseType = stmtType;
    					}
    				}
    			}
    		}
        }
         
        if (thenType != null && elseType == null && this.elseStmtList.size() == 0) {
        	System.out.println(new TypeError(super.row, super.column, "Irregular").toPrint());
        	System.exit(0);
        }
        else if ((thenType != null && elseType != null && !thenType.isSubtype(elseType)) || (thenType == null && elseType != null)) {
        	System.out.println(new TypeError(super.row, super.column, "Branches types mismatch").toPrint());
        	System.exit(0);
        }
        
        return thenType;    
	}

	@Override
	public String codeGeneration() {
		
		String truelabel = AssetLanlib.freshLabel();
		String endlabel = AssetLanlib.freshLabel();
		
		String elseCGen = "";
		
		for(Statement statement : this.elseStmtList) {
			
		elseCGen += statement.codeGeneration();
			
		}
		
		String thenCGen = "";
		
		for(Statement statement : this.thenStmtList) {
			
		thenCGen += statement.codeGeneration();
			
		}
		
		String ifcgen = this.exp.codeGeneration()+
				"li $t1 1\n"+
				"beq $a0 $t1 "+truelabel+"\n"+
				elseCGen+
				"b "+endlabel+"\n"+
				truelabel+":\n"+
				thenCGen+
				endlabel+":\n";

		return ifcgen;
		/*
		
		String truelabel = AssetLanlib.freshLabel();

        String endlabel = AssetLanlib.freshLabel();

        

        String ifcgen = this.exp.codeGeneration()+
                        "li $t1 1\n"+
                        "beq $a0 $t1 "+truelabel+"\n"+
                        this.elseStmt.codeGeneration()+
                        "b "+endlabel+"\n"+
                        truelabel+":\n"+
                        this.thenStmt.codeGeneration()+
                        endlabel+":\n";

        return ifcgen;
        */
	}

	@Override
	public void analyzeEffect(EEnvironment env) {
		this.exp.analyzeEffect(env);
		
		EEnvironment tmpEnv = env.clone();
        
        for(Statement stmt : this.thenStmtList) {
        	stmt.analyzeEffect(env);
		}
        
        for(Statement stmt : this.elseStmtList) {
        	stmt.analyzeEffect(tmpEnv);
		}
        
        env.maxModifyEnv(tmpEnv);
        
        return ;
	}

}
