package ast.dec;

import java.util.ArrayList;

import ast.exp.Exp;
import util.EEnvironment;
import util.Environment;
import util.STEnvironment;
import util.SemanticError;
import util.TypeError;
import ast.type.Type;
import ast.type.VoidType;

//Used for a declaration of an ID and in case also for the assignment of it
public class FieldNode extends VarNode {
	
	private Exp exp;

	public FieldNode(int row, int column, Type type, String id, Exp exp) {
		super(row, column, type, id);
		this.exp = exp;
	}
	
	public FieldNode(int row, int column, Type type, String id) {
		super(row, column, type, id);
		this.exp = null;
	}

	public Exp getExp() {
		return this.exp;
	}
	
	@Override
	public String toPrint(String indent) {
		String s = super.toPrint(indent);
		return indent + "Field:\n" + indent + "\t" + s + (this.exp != null ? 
				"\n" + indent + "\t=\n" + this.exp.toPrint(indent + "\t") : ""); 
	}

	@Override
	public Type typeCheck() {
		if (this.exp != null) {
			Type expType = this.exp.typeCheck();

	        if (!super.type.isSubtype(expType)) {
	            System.out.println(new TypeError(this.exp.getRow(), this.exp.getColumn(), "Expression type ["
	                    + expType.getType() + "] is not equal to declared type [" + super.type.getType() + "]").toPrint());
	            System.exit(0);
	        }
		}

        return new VoidType();
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		ArrayList<SemanticError> errors = super.checkSemantics(env);
		if (exp != null)
			errors.addAll(exp.checkSemantics(env));
		return errors;
	}

	
	@Override
	public void analizeEffect(EEnvironment env) {
		if (exp != null)
			this.exp.analizeEffect(env);
		return ;
	}
}

