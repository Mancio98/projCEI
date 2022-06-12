package ast.statement;

import java.util.ArrayList;
import java.util.HashMap;

import ast.IdNode;
import ast.exp.Exp;
import util.SemanticError;
import util.EEntry;
import util.EEntryAsset;
import util.EEntryFun;
import util.EEnvironment;
import util.Environment;
import util.STEnvironment;
import ast.type.Type;
import ast.type.VoidType;
import ast.type.BoolType;
import util.TypeError;

//Used for rule like "if ( exp ) statement (else statement)?"
public class IteStmt extends Statement {

	private Exp exp;
	private ArrayList<Statement> thenStmtList, elseStmtList;

	/*public IteStmt(int row,int column,Exp exp, ArrayList<Statement> thenStmt) {
		super(row, column);
		this.exp = exp;
		this.thenStmtList = thenStmt;
		this.elseStmtList = null;
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
	public void analizeEffect(EEnvironment env) {
		this.exp.analizeEffect(env);
		
		EEnvironment tmpEnv = env.clone();
        
        for(Statement stmt : this.thenStmtList) {
        	stmt.analizeEffect(env);
		}
        
        for(Statement stmt : this.elseStmtList) {
        	stmt.analizeEffect(tmpEnv);
		}

        env.maxModifyEnv(tmpEnv);
        
        return ;
	}
	
	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	public void analizeLiquidity(EEnvironment env, EEnvironment gEnv, String f) {
		System.out.println("ITE");
		System.out.println(f);
		for (String s : gEnv.getAllFun().keySet()) {
			System.out.println(s);
			System.out.println(((EEntryFun)(gEnv.lookUp(s))));
		}
		EEnvironment tmpEnv = env.clone();

        for(Statement stmt : this.thenStmtList) {
			
			if (stmt instanceof CallStmt) {
				if (!((CallStmt)(stmt)).getId().equals(f)) {

					for (String g : env.getAllAsset().keySet()) {
						((EEntryAsset)(env.lookUp(g))).updateEffectState(((EEntryAsset)(gEnv.lookUp(g))).getEffectState());
					}

					((EEntryFun)(gEnv.lookUp(((CallStmt)(stmt)).getId()))).getFunNode().analyzeLiquidity(gEnv);
				}
			}
			else {
				if (stmt instanceof IteStmt) {
					((IteStmt)(stmt)).analizeLiquidity(env, gEnv, f);
				}
				else {
					stmt.analizeLiquidity(env);
				}
			}
		}
        
        for(Statement stmt : this.elseStmtList) {
			
			if (stmt instanceof CallStmt) {
				if(!((CallStmt)(stmt)).getId().equals(f)) {
					
					for (String g : tmpEnv.getAllAsset().keySet()) {
						((EEntryAsset)(tmpEnv.lookUp(g))).updateEffectState(((EEntryAsset)(gEnv.lookUp(g))).getEffectState());
					}
					
					((EEntryFun)(gEnv.lookUp(((CallStmt)(stmt)).getId()))).getFunNode().analyzeLiquidity(gEnv);
				}	
			}
			else {
				if (stmt instanceof IteStmt) {
					((IteStmt)(stmt)).analizeLiquidity(tmpEnv, gEnv, f);
				}
				else {
					stmt.analizeLiquidity(tmpEnv);
				}
			}
		}

        env.maxModifyEnv(tmpEnv);
        
        return ;
	}
	
	public void analizeEffectFixPoint(EEnvironment env, EEnvironment gEnv, String f) {
		this.exp.analizeEffect(env);
		
		EEnvironment tmpEnv = env.clone();
        
        for(Statement stmt : this.thenStmtList) {
			
			if (stmt instanceof CallStmt && ((CallStmt)(stmt)).getId().equals(f)) {	// CAMBIARE CON ! equals()

				for (String g : env.getAllAsset().keySet()) {
					((EEntryAsset)(env.lookUp(g))).updateEffectState( ((EEntryAsset)( ((EEntryFun)(gEnv.lookUp(f))).getEnv1().getAllAsset().get(g) )).getEffectState());
				}
			}
			else {
				// DA CAMBIARE
				if (stmt instanceof IteStmt) {
					((IteStmt)(stmt)).analizeEffectFixPoint(env, gEnv, f);
				}
				else {
					stmt.analizeEffect(env);
				}
			}
		}
        
        for(Statement stmt : this.elseStmtList) {
			
			if (stmt instanceof CallStmt && ((CallStmt)(stmt)).getId().equals(f)) {
				
				for (String g : tmpEnv.getAllAsset().keySet()) {
					((EEntryAsset)(tmpEnv.lookUp(g))).updateEffectState( ((EEntryAsset)( ((EEntryFun)(gEnv.lookUp(f))).getEnv1().getAllAsset().get(g) )).getEffectState());
				}
			}
			else {
				// DA CAMBIARE
				if (stmt instanceof IteStmt) {
					((IteStmt)(stmt)).analizeEffectFixPoint(tmpEnv, gEnv, f);
				}
				else {
					stmt.analizeEffect(tmpEnv);
				}
			}
		}
        
        env.maxModifyEnv(tmpEnv);

        return ;
	}

	@Override
	public void analizeLiquidity(EEnvironment env) {
		// TODO Auto-generated method stub
		
	}

}
