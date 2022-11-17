package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.type.Type;
import ast.type.AssetType;
import ast.type.VoidType;
import util.SemanticError;
import util.TypeError;
import util.EEntryAsset;
import util.EEnvironment;
import util.STEnvironment;

//Used for rule like "transfer ID"
public class TransferStmt extends Statement {

	private final IdNode id;

	public TransferStmt(int row,int column,IdNode id) {
		super(row, column);
		this.id = id;
	}

	@Override
	public String toPrint(String indent) {
		return indent + "Transfer:\n" + this.id.toPrint(indent + "\t");
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		errors.addAll(this.id.checkSemantics(env));
		return errors;
	}

	@Override
	public Type typeCheck() {
		Type typeId = this.id.typeCheck();
		
		if (!(typeId != null && this.id.getSTentry().getType() instanceof AssetType)) {
			System.out.println(new TypeError(super.row, super.column,
								"Cannot transfer from [" + typeId.getType() + "] to [" + typeId.getType() + "]").toPrint());
			System.exit(0);
		}
		return null;
	}

	@Override
	public String codeGeneration() {
		
		String alcgen = "";
		
		for(int i=0; i < (this.id.getNestingLevel() - this.id.getSTentry().getNestinglevel()); i++) {
			alcgen += "lw $al $al 0\n";
		}
		
		String transfcgen = "move $al $fp\n"+
				alcgen+
				"addi $al $al "+this.id.getSTentry().getOffset()+"\n"+
				"move $a0 $al\n"+
				"transf $a0\n";
		
		return transfcgen;
	}

	@Override
	public void analyzeEffect(EEnvironment env) {
		((EEntryAsset)(env.lookUp(this.id.getId()))).updateEffectState("0");
		return ;
	}

	@Override
	public void analyzeLiquidity(EEnvironment env, String f) {
		((EEntryAsset)(env.lookUp(this.id.getId()))).updateEffectState("0");
		return ;
	}

	@Override
	public void analyzeEffectFixPoint(EEnvironment env, EEnvironment gEnv, String f) {
		((EEntryAsset)(env.lookUp(this.id.getId()))).updateEffectState("0");
		return ;
	}

}
