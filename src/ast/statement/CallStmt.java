package ast.statement;

import java.util.ArrayList;
import java.util.List;
import ast.exp.Exp;
import util.Environment;
import util.Environment.UndeclaredIdException;
import util.SemanticError;
import ast.type.Type;
import ast.type.FunType;
import ast.IdNode;
import util.STentry;
import util.TypeError;

//Used to the management of function's call
public class CallStmt extends Statement {
	
	private String id;
	private List<Exp> exp;
	private List<IdNode> idList;
	private STentry entry;

	public CallStmt(int row, int column, String id, ArrayList<Exp> exp, ArrayList<IdNode> idList) {
		super(row, column);
		this.id = id;
		this.exp = exp;
		this.idList = idList;
	}
	
	@Override
	public String toPrint(String indent) {
		String s = indent + "Call:\n" + indent + "\tFun: " + this.id ;
		s += "\n" + indent + "\t(";
		int i = 0;
		for(Exp e : this.exp) {
			if(i == 0) {
				i++;
				s += "\n" + e.toPrint(indent + "\t\t");
			}
			else {
				s += "\n" + e.toPrint(indent + "\t\t");
			}
			s += " ,";
		}
		
		if (i == 0) {
			s += " )\n" + indent + "\t[";
		}
		else {
			s = s.substring(0, s.length() - 2);
			s += "\n" + indent + " \t)\n" + indent + "\t[";
		}
		
		i = 0;
		for(IdNode n : idList) {
			if (i == 0) {
				i++;
				s += "\n" + n.toPrint(indent + "\t\t");
			}
			else {
				s += "\n" + n.toPrint(indent + "\t\t");
			}
			s += " ,";
		}
		
		if (i == 0) {
			s += " ]";
		}
		else {
			s = s.substring(0, s.length() - 2);
			s += "\n" + indent + "\t]";
		}
		return s;
	}
	
	@Override
	public Type typeCheck() {
		/*
		if (this.entry == null)
            return null;

        /// Controllo che entry.getType sia una funzione
        if (!(this.entry.getType() instanceof FunType)) {
            new TypeError(super.row, super.column,
                    "[" + this.entry.getId() + "] is not a function");
        }

        
        // FINIRE DI CONTROLLARE DA QUI
        FunType typeFun = ((FunType) (this.entry.getType()));
        if (parlist.size() != typeFun.getArguments().size())
            return null;

        for (int i = 0; i < parlist.size(); i++) {
            Type funParType = typeFun.getArguments().get(i).typeCheck();
            if (funParType == null) {
                new TypeError(row, column,
                        "[" + entry.getId() + "] is not a function");
                return null;
            }

            Type callParType = parlist.get(i).typeCheck();

            if (!funParType.equals(callParType)) {
                new TypeError(row, column,
                        "Argument " + i + " must be of type [" + funParType.getType() + "]");
                return null;
            }
        }

        return typeFun.getReturnType();
        */
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		
		try {
			this.entry = env.lookUp(this.id);
		}
		catch (UndeclaredIdException e) {
			errors.add(new SemanticError(super.row, super.column, " " + this.id + " undeclared"));
		}
		
		for(Exp nodeExp : this.exp) {
			errors.addAll(nodeExp.checkSemantics(env));
		}
			
		for(IdNode nodeId : this.idList) {
			errors.addAll(nodeId.checkSemantics(env));
		}
		
		return errors;
	}

}
