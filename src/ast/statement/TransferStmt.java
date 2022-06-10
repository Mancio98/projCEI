package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.type.Type;
import ast.type.AssetType;
import ast.type.VoidType;
import util.SemanticError;
import util.TypeError;
import util.EEntry;
import util.EEntryAsset;
import util.EEnvironment;
import util.EEnvironment.EffectState;
import util.Environment;
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
			// DA CAMBIARE IL SECONOD PARAMETRO NEL MESSAGGIO, SCRIVERE L'ASSET GLOBALE DEL PROGRAMMA
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
		((EEntryAsset)(env.lookUp(this.id.getId()))).updateEffectState("0");
		return ;
	}

}
