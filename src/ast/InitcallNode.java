package ast;

import java.util.ArrayList;

import ast.exp.Exp;
import util.Environment;
import util.STentry;
import util.Environment.UndeclaredIdException;
import util.EnvironmentAsset;
import util.SemanticError;
import util.TypeError;
import ast.type.FunType;
import ast.type.IntType;
import ast.type.Type;
import ast.type.VoidType;

// DA MODIFICARE COME CallNode

////Used to the management of initial function's call
public class InitcallNode extends Node {
	private String id;
	private ArrayList<Exp> exp1List;
	private ArrayList<Exp> exp2List;
	private STentry entry;
	private int nestingLevel;

	public InitcallNode(int row, int column, String id, ArrayList<Exp> exp1List, ArrayList<Exp> exp2List) {
		super(row, column);
		this.id = id;
		this.exp1List = exp1List;
		this.exp2List = exp2List;
		this.entry = null;
	}
	
	/*
	@Override
	public String toPrint(String indent) {
		String s = indent + "Initcall:\n" + indent + "\tFun: " + this.id ;
		s += " (";
		int i=0;
		for(Exp e : exp1) {
			if(i==0) {
				i++;
				s += "\n" + e.toPrintInFun(" ");
			}
			else {
				s += "\n" + e.toPrintInFun(", ");
			}
		}
		s+= " )\n[\n";
		i=0;
		for(Exp e : exp2) {
			if (i==0){
				i++;
				s += "\n" + e.toPrintInFun(" ");
			}
			else {
				s += "\n" + e.toPrintInFun(", ");
			}
		}
		s+= " ]";
		return s;
	}
	*/
	
	@Override
	public String toPrint(String indent) {
		String s = indent + "Initcall:\n" + indent + "\tFun: " + this.id ;
		s += "\n" + indent + "\t(";
		int i = 0;
		for(Exp e : this.exp1List) {
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
		for(Exp e : this.exp2List) {
			if (i == 0) {
				i++;
				s += "\n" + e.toPrint(indent + "\t\t");
			}
			else {
				s += "\n" + e.toPrint(indent + "\t\t");
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
        
        if (!(typeFun.getReturnType() instanceof VoidType)) {
            System.out.println(new TypeError(row, column,
        						"Invalid return type for initcall " + this.id).toPrint());
            System.exit(0);
        }
        
        if ((this.exp1List.size() != typeFun.getParTypes().size()) && (this.exp2List.size() != typeFun.getParATypes().size())) {
        	// SISTEMARE IL SYSTEM.OUT
        	System.out.println(new TypeError(super.row, super.column,
        						"[" + this.id + "] has " + (typeFun.getParTypes().size() + typeFun.getParATypes().size()) + " arguments, not " + (this.exp1List.size() + this.exp2List.size())).toPrint());
        	System.exit(0);
        }
        
        for (int i = 0; i < this.exp1List.size(); i++) {
            Type funParType = typeFun.getParTypes().get(i);
            Type callParType = this.exp1List.get(i).typeCheck();

            if (!funParType.isSubtype(callParType)) {
                System.out.println(new TypeError(row, column,
                        			"Argument " + i + " must be of type [" + funParType.getType() + "], instead of type [" + callParType.getType() + "]").toPrint());
            	System.exit(0);
            }
        }
        
        for (int i = 0; i < this.exp2List.size(); i++) {
            Type funParAType = typeFun.getParATypes().get(i);
            Type callParAType = this.exp2List.get(i).typeCheck();
            
            if (!funParAType.isSubtype(callParAType) && !callParAType.isSubtype(new IntType())) {
                System.out.println(new TypeError(super.row, super.column,
                        			"Argument " + i + " must be of type [" + funParAType.getType() + "], instead of type [" + callParAType.getType() + "]").toPrint());
            	System.exit(0);
            }
        }
        
		return typeFun.getReturnType();
	}

	@Override
	public String codeGeneration() {
		
		String paramcgen = "";
				
		for(int i = this.exp2List.size()-1; i>= 0; i--) {
			
			paramcgen += this.exp2List.get(i).codeGeneration();
			paramcgen += "push a0\n";
		}
		
		for(int i = this.exp1List.size()-1; i>= 0; i--) {
			
			paramcgen += this.exp1List.get(i).codeGeneration();
			paramcgen += "push a0\n";
		}
		
		/*
		String alcgen = "";
		
		for(int i=0; i < (this.nestingLevel - this.entry.getNestinglevel()); i++) {
			
			alcgen += "lw al 0(al)\n";
		}*/
		
		String callcgen ="push fp\n"+
						paramcgen+
						/*
						"lw al 0(fp)\n"+ //forse si può fare a meno
						alcgen+
						"push al\n"+*/
						"jal "+this.entry.getLabel();
						
		return callcgen;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		
		try {
			this.entry = env.lookUp(this.id);
		}
		catch (UndeclaredIdException e) {
			errors.add(new SemanticError(super.row, super.column, " " + id +" undeclared"));
		}
			
		for(Exp nodeExp : this.exp1List) {
			errors.addAll(nodeExp.checkSemantics(env));
		}			
		
		for(Exp nodeExp : this.exp2List) {
			errors.addAll(nodeExp.checkSemantics(env));
		}
		
		this.nestingLevel = env.getNestingLevel();
			
		return errors;
	}

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}

}
