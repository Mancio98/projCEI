package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.exp.Exp;
import util.SemanticError;
import util.EEnvironment;
import util.STEnvironment;
import util.TypeError;
import ast.type.AssetType;
import ast.type.IntType;
import ast.type.Type;

//Used for rule like "ID = exp"
public class AssignmentStmt extends Statement {

	private final IdNode left;
	private final Exp exp;
	private int nestingLevel;
	private final CallStmt call;

	
	public AssignmentStmt(int row,int column,IdNode left, Exp exp) {
		super(row, column);
		this.left = left;
		this.exp = exp;
		this.call = null;
	}
	
	public AssignmentStmt(int row,int column,IdNode left, CallStmt call) {
		super(row, column);
		this.left = left;
		this.exp = null;
		this.call = call;
	}
	
	@Override
	public String toPrint(String indent) {
		if(exp == null) {
			return indent + "Assignment:\n" + indent + "\tLeft: \n" + this.left.toPrint(indent + "\t\t") + "\n" + indent
					+ "\tRight: \n" + this.call.toPrint(indent + "\t\t");
		}
		else return indent + "Assignment:\n" + indent + "\tLeft: \n" + this.left.toPrint(indent + "\t\t") + "\n" + indent
				+ "\tRight: \n" + this.exp.toPrint(indent + "\t\t");
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		errors.addAll(this.left.checkSemantics(env));
		if(exp == null) {
			errors.addAll(this.call.checkSemantics(env));
		}
		else errors.addAll(this.exp.checkSemantics(env));
		this.nestingLevel = env.getNestingLevel();
		return errors;
	}

	@Override
	public Type typeCheck() {
		Type typeLeft = this.left.typeCheck();
		Type typeExp;
		
		if (exp == null) {
			typeExp = this.call.typeCheck();
		}
		else {
			typeExp = this.exp.typeCheck();
		}
		 

		if(typeLeft.isSubtype(new IntType()) && typeExp.isSubtype(new AssetType())) {
			return null;	
		}
		
		if(typeLeft.isSubtype(new AssetType())&& typeExp.isSubtype(new AssetType())) {
			System.out.println(new TypeError(super.row, super.column, 
					"Cannot assign [" + typeExp.getType() + "] to [" + typeLeft.getType() + "]").toPrint());
				System.exit(0);
		}
		
		if (!typeLeft.isSubtype(typeExp)) {
			System.out.println(new TypeError(super.row, super.column, 
								"Cannot assign [" + typeExp.getType() + "] to [" + typeLeft.getType() + "]").toPrint());
            System.exit(0);
		}
		
		return null;
	}

	@Override
	public String codeGeneration() {
		
		String expcgen = "";
		
		if (exp == null) {
			expcgen += this.call.codeGeneration();
		}
		else {
			expcgen += this.exp.codeGeneration();
		}
		
		String alcgen = "";
		
		for(int i=0; i < (this.nestingLevel - this.left.getSTentry().getNestinglevel()); i++) {
			alcgen += "lw $al $al 0\n";
		}
		String asgmcgen = expcgen+
						"move $al $fp\n"+
						alcgen+
						"sw $a0 $al "+this.left.getSTentry().getOffset()+"\n";
		
		return asgmcgen;
	}

	@Override
	public void analyzeEffect(EEnvironment env) {
		return ;
	}
	
	@Override
	public void analyzeLiquidity(EEnvironment env) {
		return ;
	}

}
