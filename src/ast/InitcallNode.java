package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import ast.dec.AdecNode;
import ast.dec.AssetNode;
import ast.exp.Exp;
import util.EEntry;
import util.EEntryAsset;
import util.EEnvironment;
import util.EffectError;
import util.STentry;
import util.EEntryFun;
import util.STEnvironment;
import util.STEnvironment.UndeclaredIdException;
import util.SemanticError;
import util.TypeError;
import ast.type.FunType;
import ast.type.IntType;
import ast.type.Type;
import ast.type.VoidType;

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
	
	public String getId() {
		return this.id;
	}
	
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
			paramcgen += "push $a0\n";
		}
		
		for(int i = this.exp1List.size()-1; i>= 0; i--) {
			
			paramcgen += this.exp1List.get(i).codeGeneration();
			paramcgen += "push $a0\n";
		}
		
		
		String alcgen = "";
		
		for(int i=0; i < (this.nestingLevel - this.entry.getNestinglevel()); i++) {
			
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
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		this.nestingLevel = env.getNestingLevel();
		
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
			
		return errors;
	}
	
	// Auxiliary function to set entry environment of the function with the current assets value
	// with a pre-evaluation of the expression passed as asset
	private void substituteEnv0(EEnvironment env, EEnvironment env0, EEnvironment env1) {
		Exp exp;
		EEntryAsset par;
		ArrayList<AssetNode> parAsset = ((FunType)(this.entry.getType())).getParAsset();
		
		for (int pos = this.exp2List.size() - 1; pos >= 0; pos--) {
			exp = this.exp2List.get(pos);
			par = (EEntryAsset) env0.lookUp(parAsset.get(pos).getId());
			
			// Pre-evaluation
			int valExp = exp.calculateExp();
			// Setting the effect
			if (valExp > 0) {
				par.updateEffectState("1");
			}
			else {
				par.updateEffectState("0");
			}
		}
		return ;
	}
	
	// Auxiliary function that computes the exit environment from the entry environment created previously
	private void calculateEnv1(EEnvironment env, EEnvironment env0, EEnvironment env1) {
		int i;
		String[] split;
		HashMap<String, EEntry> subs = env0.getAllAsset();
		HashMap<String, EEntry> map = env1.getAllAsset();
		for (String id : map.keySet()) {
			// Check if the effect depends on other terms of it is just 0 or 1
			if (!((EEntryAsset)(map.get(id))).getEffectState().equals("1") && !((EEntryAsset)(map.get(id))).getEffectState().equals("0")) {
				
				// If the effects depends on other terms, we replace them and compute the abstract sum
				split = (((EEntryAsset)(map.get(id))).getEffectState()).split(Pattern.quote("+"));
				i = 1;
				String res = ((EEntryAsset)(subs.get(split[0]))).getEffectState();
				while (i < split.length) { 
					res = EEntryAsset.effectStatePlus(res, ((EEntryAsset)(subs.get(split[i]))).getEffectState());
					i++;
				}
				((EEntryAsset)(map.get(id))).updateEffectState(res);
			}
		}
		return ;
	}
	
	
	@Override
	public void analyzeEffect(EEnvironment env) {
		ArrayList<String> liq = new ArrayList<String>();

		EEnvironment l0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0().clone();
		EEnvironment l1 = ((EEntryFun)(env.lookUp(this.id))).getEnv1().clone();
		
		// Compute the entry enviroment based on current value
		substituteEnv0(env, l0, l1);
		// Compute the exit value of assets based on the exit enviroment created previously
		calculateEnv1(env, l0, l1);
		
		// Update the main environment with the effect value calculated in the last step
		HashMap<String, EEntry> map = l1.getAllAsset();
		for (String id : map.keySet()) {
			// Check global assets
			if (env.lookUp(id) != null) {
				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map.get(id))).getEffectState());
			}
		}
		
		// Check if all the value assets are 0
		for (String as : env.getAllAsset().keySet()) {
			if (!((EEntryAsset)(env.lookUp(as))).getEffectState().equals("0")) {
				liq.add(as);
			}
		}
		
		// If only just one global asset value is not 0, the program is not liquid
		if (!liq.isEmpty()) {
			String msg = "";
			for (String a : liq) {
				if (msg.isEmpty()) {
					msg = a;
				}
				else {
					msg = msg + ", " + a;
				}
			}
			System.out.println(new EffectError(row, column, "Program is not liquid. [" + msg + "] not empty").toPrint());
			System.exit(0);
		}
		
		return ;

	}
	
	// Liquidity analysys
	public void analyzeLiquidity(EEnvironment env) {
		Exp exp;
		EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		
		AdecNode adec = ((EEntryFun)(env.lookUp(this.id))).getFunNode().getParAdec();
		for (int pos = adec.getListAdec().size() - 1; pos >= 0; pos--) {
			AssetNode a = adec.getListAdec().get(pos);
			exp = this.exp2List.get(pos);
			
			// Pre-evaluation
			int valExp = exp.calculateExp();
			if (valExp > 0) {
				((EEntryAsset)(env0.lookUp(a.getId()))).updateEffectState("1");
			}
			else if(valExp == 0) {
				((EEntryAsset)(env0.lookUp(a.getId()))).updateEffectState("0");
			}
			else {
				// The case less than 0 should never happen
				System.out.println(new EffectError(row, column, "Passing negative value with move is not possibile").toPrint());
				System.exit(0);
			}
		}
		
		// Liquidity analysys of the called function
		((EEntryFun)(env.lookUp(this.id))).getFunNode().analyzeLiquidity(env);
		
		return ;
	}
}
