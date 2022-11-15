package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.exp.CallExp;
import ast.exp.Exp;
import util.SemanticError;
import util.AssetLanlib;
import util.EEntryAsset;
import util.EEntryFun;
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
	
	public IteStmt(int row,int column,Exp exp, ArrayList<Statement> thenStmt, ArrayList<Statement> elseStmt) {
		super(row, column);
		this.exp = exp;
		this.thenStmtList = thenStmt;
		this.elseStmtList = elseStmt;
	}

	public Exp getExp() {
		return this.exp;
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
	}

	// Funzione di analisi degli effetti base, ovvero senza possibile chiamata ricorsiva in uno dei rami
	@Override
	public void analyzeEffect(EEnvironment env) {
		System.out.println("IF");
		// Analisi della guardia dell'IF
		this.exp.analyzeEffect(env);
		
		// Creo un ambiente ulteriore per tenere traccia delle modifiche di entrambi rami dell'IF
		EEnvironment tmpEnv = env.clone();
		
		for (String fun : env.getAllFun().keySet()) {
			((EEntryFun)(tmpEnv.lookUp(fun))).setFunNode(((EEntryFun)(env.lookUp(fun))).getFunNode());
		}

		System.out.println("THEN");
		// Analizzo tutti gli statement del ramo THEN
        for(Statement stmt : this.thenStmtList) {
        	stmt.analyzeEffect(env);
		}
        
        System.out.println("ELSE");
        // Analizzo tutti gli statement del ramo ELSE
        for(Statement stmt : this.elseStmtList) {
        	stmt.analyzeEffect(tmpEnv);
		}

        // Una volta analizzati i due rami, utilizzo una funzione ausiliaria per controllare quale sia il massimo degli effeti per ogni asset
        env.maxModifyEnv(tmpEnv);
        
        return ;
	}
	
	// COME IN FunNode VERIFICARE IL CASO DELLA CHIAMATA RICORSIVA
	@Override
	public void analyzeLiquidity(EEnvironment env, String f) {
		System.out.println("IF");
		// Analizzo l'espressione nella guardia dell'IF
		this.exp.analyzeLiquidity(env, f);
		
		EEnvironment tmpEnv = env.clone();
		for (String fun : env.getAllFun().keySet()) {
			((EEntryFun)(tmpEnv.lookUp(fun))).setFunNode(((EEntryFun)(env.lookUp(fun))).getFunNode());
		}
		
		System.out.println("THEN");
		// Analizzo tutti gli statement del ramo THEN
		for(Statement stmt : this.thenStmtList) {
			stmt.analyzeLiquidity(env, f);
		}
		
		System.out.println("ELSE");
        // Analizzo tutti gli statement del ramo ELSE
		for(Statement stmt : this.elseStmtList) {
			stmt.analyzeLiquidity(tmpEnv, f);
		}
				
        // Una volta analizzati i due rami, utilizzo una funzione ausiliaria per controllare quale sia il massimo degli effeti per ogni asset
        env.maxModifyEnv(tmpEnv);
        
        return ;
	}
	
	// Funzione ausiliaria utile per analizzare lo statement IF quando calcoliamo il punto fisso di una funzione ricorsiva
	@Override
	public void analyzeEffectFixPoint(EEnvironment env, EEnvironment gEnv, String f) {
		System.out.println("IF FIX POINT");
		// Analisi della guardia dell'IF
		this.exp.analyzeEffectFixPoint(env, gEnv, f);
		
		EEnvironment tmpEnv = env.clone();
		//EEnvironment tmpGEnv = gEnv.clone();
		
		System.out.println("THEN FIX POINT");
		// Analizzo tutti gli statement del ramo THEN
        for(Statement stmt : this.thenStmtList) {
        	stmt.analyzeEffectFixPoint(env, gEnv, f);
		}
        
        System.out.println("ELSE FIX POINT");
        // Analizzo tutti gli statement del ramo ELSE
        for(Statement stmt : this.elseStmtList) {
        	stmt.analyzeEffectFixPoint(tmpEnv, gEnv, f);
		}
        
        // Una volta analizzati i due rami, utilizzo una funzione ausiliaria per controllare quale sia il massimo degli effeti per ogni asset
        env.maxModifyEnv(tmpEnv);
        /*
        System.out.println("MAX MODIFY ENV");
        env.getSymTable().forEach(  hashmap -> {
			System.out.println();
			hashmap.forEach( (id, entry) -> {
				if (entry instanceof EEntryAsset) {
					System.out.println(id);
					System.out.println(((EEntryAsset)(entry)).getEffectState());
				}
			});
		});
        System.out.println("MAX MODIFY ENV");
		*/
        return ;
	}
}

