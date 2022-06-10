package ast.statement;

import java.util.ArrayList;
import java.util.List;
import ast.exp.Exp;
import util.AssetLanlib;
import util.Environment;
import util.Environment.UndeclaredIdException;
import util.EnvironmentAsset;
import util.SemanticError;
import ast.type.Type;
import ast.type.VoidType;
import ast.type.BoolType;
import ast.type.FunType;
import ast.IdNode;
import util.STentry;
import util.TypeError;

//Used to the management of function's call
public class CallStmt extends Statement {
	
	private String id;
	private List<Exp> expList;
	private List<IdNode> idList;
	private STentry entry;
	private int nestingLevel;

	public CallStmt(int row, int column, String id, ArrayList<Exp> exp, ArrayList<IdNode> idList) {
		super(row, column);
		this.id = id;
		this.expList = exp;
		this.idList = idList;
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public String toPrint(String indent) {
		String s = indent + "Call:\n" + indent + "\tFun: " + this.id ;
		s += "\n" + indent + "\t(";
		int i = 0;
		for(Exp e : this.expList) {
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
        if (!(this.entry.getType() instanceof FunType)) {
            System.out.println(new TypeError(super.row, super.column,
                    			"[" + this.id + "] is not a function").toPrint());
            System.exit(0);
        }

        FunType typeFun = ((FunType) (this.entry.getType()));
        
        if ((this.expList.size() != typeFun.getParTypes().size()) && (this.idList.size() != typeFun.getParATypes().size())) {
        	// SISTEMARE IL SYSTEM.OUT
        	System.out.println(new TypeError(super.row, super.column,
        						"[" + this.id + "] has " + (typeFun.getParTypes().size() + typeFun.getParATypes().size()) + " arguments, not " + (this.expList.size() + this.idList.size())).toPrint());
        	System.exit(0);
        }
        
        for (int i = 0; i < this.expList.size(); i++) {
            Type funParType = typeFun.getParTypes().get(i);
            Type callParType = this.expList.get(i).typeCheck();

            if (!funParType.isSubtype(callParType)) {
                System.out.println(new TypeError(row, column,
                        			"Argument " + i + " must be of type [" + funParType.getType() + "], instead of type [" + callParType.getType() + "]").toPrint());
            	System.exit(0);
            }
        }
        
        for (int i = 0; i < this.idList.size(); i++) {
            Type funParAType = typeFun.getParATypes().get(i);
            Type callParAType = this.idList.get(i).typeCheck();

            if (!funParAType.isSubtype(callParAType)) {
                System.out.println(new TypeError(row, column,
                        			"Argument " + i + " must be of type [" + funParAType.getType() + "], instead of type [" + callParAType.getType() + "]").toPrint());
            	System.exit(0);
            }
        }

		return typeFun.getReturnType();
	}

	@Override
	public String codeGeneration() {
		
		String paramcgen = "";
		
		for(int i = this.idList.size()-1; i>= 0; i--) {
			
			paramcgen += this.idList.get(i).codeGeneration();
			paramcgen += "push $a0\n";
		}
		
		for(int i = this.expList.size()-1; i>= 0; i--) {
			
			paramcgen += this.expList.get(i).codeGeneration();
			paramcgen += "push $a0\n";
		}
		
		
		String alcgen = "";
		
		for(int i=0; i < (this.nestingLevel- this.entry.getNestinglevel()); i++) {
			
			alcgen += "lw $al $al 0\n";
		}
		
		String callcgen ="push $fp\n"+
						paramcgen+
						"lw $al $fp 0\n"+
						alcgen+
						"push $al\n"+
						"jal "+this.entry.getLabel()+"\n";
						
		return callcgen;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		
		this.nestingLevel = env.getNestingLevel();
		try {
			this.entry = env.lookUp(this.id);
		}
		catch (UndeclaredIdException e) {
			errors.add(new SemanticError(super.row, super.column, " " + this.id + " undeclared"));
		}
		
		for(Exp nodeExp : this.expList) {
			errors.addAll(nodeExp.checkSemantics(env));
		}
			
		for(IdNode nodeId : this.idList) {
			errors.addAll(nodeId.checkSemantics(env));
		}
		
		return errors;
	}

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}

}
