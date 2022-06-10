package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.exp.Exp;
import util.SemanticError;
import util.Environment;
import util.EnvironmentAsset;
import util.TypeError;
import ast.type.Type;
import ast.type.VoidType;

//Used for rule like "ID = exp"
public class AssignmentStmt extends Statement {

	private final IdNode left;
	//private final String id;
	private final Exp exp;
	private int nestingLevel;

	
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
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		errors.addAll(this.left.checkSemantics(env));
		errors.addAll(this.exp.checkSemantics(env));
		this.nestingLevel = env.getNestingLevel();
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
		
		return new VoidType();
	}

	@Override
	public String codeGeneration() {
		
		String expcgen = this.exp.codeGeneration();
		
		String alcgen = "";
		
		for(int i=0; i < (this.nestingLevel - this.left.getSTentry().getNestinglevel()); i++) {
			//alcgen += "lw $al $al 0\n";
			alcgen += "move $al $al 0\n";
		}
		String asgmcgen = expcgen+
						"move $al $fp\n"+
						alcgen+
						"sw $a0 $al "+this.left.getSTentry().getOffset()+"\n";
		
		return asgmcgen;
	}

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}

}
