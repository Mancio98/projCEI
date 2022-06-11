package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

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
	
	public String getId() {
		return this.id;
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
						//"lw $al $fp 0\n"+ //forse si può fare a meno
						"move $al $fp\n"+
						alcgen+
						//"move $fp $sp\n"+
						"push $al\n"+
						"jal "+this.entry.getLabel()+"\n";
						
		return callcgen;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
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
			
		return errors;
	}

	private void substituteEnv0(EEnvironment env, EEnvironment env0, EEnvironment env1) {
		//EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		Exp exp;
		EEntryAsset par;
		ArrayList<AssetNode> parAsset = ((FunType)(this.entry.getType())).getParAsset();
		
		for (int pos = this.exp2List.size() - 1; pos >= 0; pos--) {
			exp = this.exp2List.get(pos);
			par = (EEntryAsset) env0.lookUp(parAsset.get(pos).getId());
			int valExp = exp.calculateExp();
			if (valExp > 0) {
				par.updateEffectState("1");
			}
			else {
				par.updateEffectState("0");
			}
		}
		/*
		HashMap<String, EEntry> map = env0.getAllAsset();
		for (String id : map.keySet()) {
			if (((EEntryAsset)(map.get(id))).getEffectState() != "1" || ((EEntryAsset)(map.get(id))).getEffectState() != "0") {
				if (env.lookUp(id) != null) {
					((EEntryAsset)(map.get(id))).updateEffectState(((EEntryAsset)(env.lookUp(id))).getEffectState());
				}
			}
		}
		*/
		return ;
	}
	
	private void calculateEnv1(EEnvironment env, EEnvironment env0, EEnvironment env1) {
		//EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		//EEnvironment env1 = ((EEntryFun)(env.lookUp(this.id))).getEnv1();
		
		int i;
		String[] split;
		HashMap<String, EEntry> subs = env0.getAllAsset();
		HashMap<String, EEntry> map = env1.getAllAsset();
		for (String id : map.keySet()) {
			if (((EEntryAsset)(map.get(id))).getEffectState() != "1" && ((EEntryAsset)(map.get(id))).getEffectState() != "0") {
				split = (((EEntryAsset)(map.get(id))).getEffectState()).split(Pattern.quote("+"));
				//split = ((((EEntryAsset)(map.get(id))).getEffectState()).contains("+") ? (((EEntryAsset)(map.get(id))).getEffectState()).split("+") : new String[] {((EEntryAsset)(map.get(id))).getEffectState()});
				/*
				System.out.println("sp");
				for (int b=0; b < split.length; b++) {
					System.out.println(split[b]);
				}
				*/
				i = 1;
				String res = ((EEntryAsset)(subs.get(split[0]))).getEffectState();
				while (i < split.length) { // (((EEntryAsset)(map.get(id))).getEffectState()).length() > 1 && 
					//System.out.println(((EEntryAsset)(subs.get(split[i]))).getEffectState());
					EEntryAsset.effectStatePlus(res, ((EEntryAsset)(subs.get(split[i]))).getEffectState());
					//EEntryAsset.effectStatePlus(((EEntryAsset)(subs.get(split[i]))).getEffectState(), (((EEntryAsset)(map.get(id))).getEffectState()).substring((((EEntryAsset)(map.get(id))).getEffectState()).indexOf("+"), (((EEntryAsset)(map.get(id))).getEffectState()).length() - 1));
					i++;
				}
				//System.out.println(res);
				((EEntryAsset)(map.get(id))).updateEffectState(res);
			}
		}
		/*
		System.out.println("VED");
		for (String st : map.keySet()) {
			System.out.println(st);
			System.out.println(((EEntryAsset)(map.get(st))).getEffectState());
		}
		System.out.println("VED");
		*/
		return ;
	}
	
	@Override
	public void analyzeEffect(EEnvironment env) {
		boolean liquidity = true;
		/*EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		EEnvironment env1 = ((EEntryFun)(env.lookUp(this.id))).getEnv1();
		
		EEnvironment clone0 = env0.clone();
		EEnvironment clone1 = env1.clone();
		substituteEnv0(env, clone0, clone1);
		calculateEnv1(env, clone0, clone1);
		
		ArrayList<AssetNode> parAsset = ((FunType)(this.entry.getType())).getParAsset();
		
		HashMap<String, EEntry> map0 = clone0.getAllAsset();
		for (String id : map0.keySet()) {
			if (env.lookUp(id) != null) {
				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map0.get(id))).getEffectState());
			}
		}
		
		for (AssetNode parFor : parAsset) {
			if (((EEntryAsset)(clone1.lookUp(parFor.getId()))).getEffectState() != "0") {
				liquidity = false;
			}
		}
		
		HashMap<String, EEntry> map = clone1.getAllAsset();
		for (String id : map.keySet()) {
			if (env.lookUp(id) != null) {
				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map.get(id))).getEffectState());
			}
		}*/
		/*System.out.println("INIT");
		for (String as : env.getAllAsset().keySet()) {
			System.out.println(as);
			System.out.println(((EEntryAsset)(env.lookUp(as))).getEffectState());
		}
		System.out.println("INIT");*/
		EEnvironment l0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0().clone();
		EEnvironment l1 = ((EEntryFun)(env.lookUp(this.id))).getEnv1().clone();
		
		substituteEnv0(env, l0, l1);
		calculateEnv1(env, l0, l1);
		
		HashMap<String, EEntry> map = l1.getAllAsset();
		for (String id : map.keySet()) {
			//System.out.println(id);
			//System.out.println(((EEntryAsset)(map.get(id))).getEffectState());
			if (env.lookUp(id) != null) {
				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map.get(id))).getEffectState());
			}
		}
		
		HashMap<String, EEntry> envAsset = env.getAllAsset();
		for (String id : envAsset.keySet()) {
			if (l1.lookUp(id) != null) {
				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map.get(id))).getEffectState());
				//System.out.println(id);
				//System.out.println(((EEntryAsset)(envAsset.get(id))).getEffectState());
			}
		}
		
		for (String as : env.getAllAsset().keySet()) {
			System.out.println(as);
			System.out.println(((EEntryAsset)(env.lookUp(as))).getEffectState());
			if (((EEntryAsset)(env.lookUp(as))).getEffectState() != "0") {
				liquidity = false;
			}
		}
		
		if (!liquidity) {
			System.out.println(new EffectError(row, column, "Program is not liquid").toPrint());
			System.exit(0);
		}
		
		return ;
	}

}
