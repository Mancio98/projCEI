package ast;

import java.util.ArrayList;

import ast.exp.Exp;
import util.SemanticError;
import util.EEnvironment;
import util.STEnvironment;
import util.STEnvironment.UndeclaredIdException;
import ast.type.Type;
import util.STentry;

//Used to lookup if an ID is or not in the symbol table
public class IdNode extends Exp {

	private final String id;
    private STentry entry;
    private int nestingLevel;

	public IdNode(int row, int column, String id) {
    	super(row,column);
    	this.id = id;
    }

    public STentry getSTentry() {
    	return this.entry;
    }
    
    public String getId() {
    	return this.id;
    }
    
    public int getNestingLevel() {
 		return nestingLevel;
 	}

    
    @Override
    public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
    	
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        
        try {
			this.entry = env.lookUp(this.id);
		} catch (UndeclaredIdException e) {
			errors.add(new SemanticError(super.row, super.column, " " + this.id + " undeclared"));
		} 
        this.nestingLevel = env.getNestingLevel();  
        return errors;
    }
    
    @Override
    public String toPrint(String indent) {
        return indent + "ID: " + this.id;
    }

	@Override
	public Type typeCheck() {
		if (this.entry == null)
            System.exit(0);
        return this.entry.getType();
	}


	@Override
	public String codeGeneration() {
		
		String alcgen = "";
		
		for(int i=0; i < (this.nestingLevel - this.entry.getNestinglevel()); i++) {
			alcgen += "lw $al $al 0\n";
		}
		String idcgen = "move $al $fp\n"+
						alcgen+
						"lw $a0 $al "+this.entry.getOffset()+"\n";
		
		return idcgen;
	}
	
	public String reachId() {
		String alcgen = "move $al $fp\n";
		
		for(int i=0; i < (this.nestingLevel - this.entry.getNestinglevel()); i++) {
			alcgen += "lw $al $al 0\n";
		}
		return alcgen;
	}

	@Override
	public void analyzeEffect(EEnvironment env) {
		return ;
	}

	@Override
	public int calculateExp() {
		return 0;
	}

	@Override
	public void analyzeLiquidity(EEnvironment env, String f) {
		return;
		
	}

	@Override
	public void analyzeEffectFixPoint(EEnvironment env, EEnvironment gEnv, String f) {
		return ;
	}
    
}