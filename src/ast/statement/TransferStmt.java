package ast.statement;

import java.util.ArrayList;

import ast.IdNode;
import ast.type.Type;
import ast.type.AssetType;
import ast.type.VoidType;
import util.SemanticError;
import util.TypeError;
import util.Environment;
import util.EnvironmentAsset;

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
	public ArrayList<SemanticError> checkSemantics(Environment env) {
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
		return new VoidType();
	}

	@Override
	public String codeGeneration() {
		
		String alcgen = "";
		
		for(int i=0; i < (this.id.getNestingLevel() - this.id.getSTentry().getNestinglevel()); i++) {
			alcgen += "lw $al $al 0\n";
		}
		/*
		String transfcgen = "move $al $fp\n"+
						alcgen+
						"lw $a0 $al "+(this.id.getSTentry().getOffset()+1)+"\n"+
						"transf $a0\n"+
						"li $t1 0\n"+
						"sw $t1 $al "+(this.id.getSTentry().getOffset()+1)+"\n";*/
		
		String transfcgen = "move $al $fp\n"+
				alcgen+
				"addi $al $al "+(this.id.getSTentry().getOffset()+1)+"\n"+
				"move $a0 $al\n"+
				"transf $a0\n";
		
		return transfcgen;
	}

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		env.update(id.getId(), 0);
		return null;
	}

}
