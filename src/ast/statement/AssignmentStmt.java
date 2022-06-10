package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.exp.Exp;
import util.SemanticError;
import util.EEnvironment;
import util.Environment;
import util.STEnvironment;
import util.TypeError;
import ast.type.Type;
import ast.type.VoidType;

// CONTROLLARE ASSEGNAMENTO DI ASSET VERSO INTERI!!!

//Used for rule like "ID = exp"
public class AssignmentStmt extends Statement {

	private final IdNode left;
	//private final String id;
	private final Exp exp;

	
	public AssignmentStmt(int row,int column,IdNode left, Exp exp) {
		super(row, column);
		this.left = left;
		this.exp = exp;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + "Assignment:\n" + indent + "\tLeft: \n" + this.left.toPrint(indent + "\t\t") + "\n" + indent
				+ "\tRight: \n" + this.exp.toPrint(indent + "\t\t");
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		errors.addAll(this.left.checkSemantics(env));
		errors.addAll(this.exp.checkSemantics(env));
		return errors;
	}

	@Override
	public Type typeCheck() {
		Type typeLeft = this.left.typeCheck();
		Type typeExp = this.exp.typeCheck();

		if (!typeLeft.isSubtype(typeExp)) {
			System.out.println(new TypeError(super.row, super.column, 
								"Cannot assign [" + typeExp.getType() + "] to [" + typeLeft.getType() + "]").toPrint());
            System.exit(0);
		}
		
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void analizeEffect(EEnvironment env) {
		return ;
	}

	@Override
	public void analizeLiquidity(EEnvironment env) {
		return ;
	}

}
