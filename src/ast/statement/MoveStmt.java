package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.type.Type;
import ast.type.VoidType;
import ast.type.AssetType;
import util.SemanticError;
import util.EEntryAsset;
import util.EEnvironment;
import util.Environment;
import util.STEnvironment;
import util.TypeError;
import util.EEnvironment.EffectState;

//Used for rule like "ID -o ID"
public class MoveStmt extends Statement {

	private final IdNode left;
	private final IdNode right;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void analizeEffect(EEnvironment env) {
		((EEntryAsset)(env.lookUp(this.right.getId()))).updateEffectState(EEntryAsset.effectStatePlus(((EEntryAsset)(env.lookUp(this.left.getId()))).getEffectState(), ((EEntryAsset)(env.lookUp(this.right.getId()))).getEffectState()));
		((EEntryAsset)(env.lookUp(this.left.getId()))).updateEffectState("0");
		return ;
	}

}
