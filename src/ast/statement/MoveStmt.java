package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.type.Type;
import ast.type.AssetType;
import util.SemanticError;
import util.EEntryAsset;
import util.EEnvironment;
import util.STEnvironment;
import util.TypeError;

//Used for rule like "ID -o ID"
public class MoveStmt extends Statement {

	private final IdNode left;
	private final IdNode right;
	private int nestingLevel;

	public MoveStmt(int row,int column,IdNode left, IdNode right) {
		super(row, column);
		this.left = left;
		this.right = right;
	}

	@Override
	public String toPrint(String indent) {
		return indent + "Move:\n" + indent + "\tLeft: \n" + this.left.toPrint(indent + "\t\t") + "\n" + indent
				+ "\tRight: \n" + this.right.toPrint(indent + "\t\t");
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		errors.addAll(this.left.checkSemantics(env));
		errors.addAll(this.right.checkSemantics(env));
		
		this.nestingLevel = env.getNestingLevel();
		return errors;
	}

	@Override
	public Type typeCheck() {
		Type typeLeft = this.left.typeCheck();
		Type typeRight = this.right.typeCheck();
		
		if (!(typeLeft.isSubtype(typeRight) && this.left.getSTentry().getType() instanceof AssetType)) {
			System.out.println(new TypeError(super.row, super.column,
								"Cannot move from [" + typeRight.getType() + "] to [" + typeRight.getType() + "]").toPrint());
			System.exit(0);
		}
		
		return null;
	}

	@Override
	public String codeGeneration() {
		
		
		String alcgenright="";
		
		for(int i=0; i < (this.nestingLevel - this.right.getSTentry().getNestinglevel()); i++) {
			alcgenright += "lw $al $al 0\n";
		}
		
		String alcgenleft="";
		
		for(int i=0; i < (this.nestingLevel - this.left.getSTentry().getNestinglevel()); i++) {
			alcgenleft += "lw $al $al 0\n";
		}
		
		
		String movecgen = this.left.codeGeneration()+
						  "push $a0\n"+
						  "move $al $fp\n"+
						  alcgenright+
						  "lw $a0 $al "+this.right.getSTentry().getOffset()+"\n"+
						  "lw $t1 $sp 0\n"+
						  "pop\n"+
						  "add $a0 $t1 $a0\n"+
						  "sw $a0 $al "+this.right.getSTentry().getOffset()+"\n"+
						  "move $al $fp\n"+
						  alcgenleft+
						  "li $t1 0\n"+
						  "sw $t1 $al "+this.left.getSTentry().getOffset()+"\n";

			return movecgen;
	}
	
	
	@Override
	public void analyzeEffect(EEnvironment env) {
		((EEntryAsset)(env.lookUp(this.right.getId()))).updateEffectState(EEntryAsset.effectStatePlus(((EEntryAsset)(env.lookUp(this.left.getId()))).getEffectState(), ((EEntryAsset)(env.lookUp(this.right.getId()))).getEffectState()));
		((EEntryAsset)(env.lookUp(this.left.getId()))).updateEffectState("0");
		return ;
	}

	@Override
	public void analyzeLiquidity(EEnvironment env) {
		((EEntryAsset)(env.lookUp(this.right.getId()))).updateEffectState(EEntryAsset.effectStatePlus(((EEntryAsset)(env.lookUp(this.left.getId()))).getEffectState(), ((EEntryAsset)(env.lookUp(this.right.getId()))).getEffectState()));
		((EEntryAsset)(env.lookUp(this.left.getId()))).updateEffectState("0");
		return ;
	}

}
