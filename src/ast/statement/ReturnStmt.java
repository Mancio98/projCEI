package ast.statement;

import java.util.ArrayList;
import ast.type.Type;
import ast.type.FunType;
import ast.type.VoidType;
import ast.exp.Exp;
import util.SemanticError;
import util.Environment;
import util.STentry;
import util.TypeError;

//Used for rule like "return (exp)?"
public class ReturnStmt extends Statement {

	private final Exp exp;

	public ReturnStmt(int row,int column) {
		super(row, column);
		this.exp = null;
	}
	
	public ReturnStmt(int row, int column, Exp exp) {
		super(row, column);
		this.exp = exp;
	}

	@Override
    public String toPrint(String indent) {
        return indent + "Return" + (this.exp != null ? ":\n" + this.exp.toPrint(indent + "\t") : "");
    }

	// DA FARE IL CONTROLLO SUL TIPO DI RITORNO RICERCANDO LA FUNZIONE
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		if (this.exp != null)
            errors.addAll(this.exp.checkSemantics(env));
		
		// FARE QUI IL CONTROLLO DELLA FUNZIONE A CUI PASSARE IL VALORE DI RETURN
		// functionStEntry = null;
		
		return errors;
	}
	/*
	@Override
	public Type typeCheck() {
		Type functionType;
        if (functionStEntry == null)
            functionType = new VoidType();
        else
        	// DA SISTEMARE
        	
        	functionType = null;
            // functionType = ((FunType) functionStEntry.getType()).getType();
        
        Type returnType;
        if (this.exp == null)
            returnType = new VoidType();
        else
            returnType = this.exp.typeCheck();

        if (!functionType.equals(returnType)) {
            new TypeError(super.row, super.column, "Return type must be [" + functionType.getType() + "]");
        }
        return returnType;
	}
	*/
	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

}
