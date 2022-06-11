package ast.statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import ast.exp.Exp;
import util.EEntry;
import util.EEntryAsset;
import util.EEntryFun;
import util.EEnvironment;
import util.STEnvironment;
import util.STEnvironment.UndeclaredIdException;
import util.SemanticError;
import ast.type.Type;
import ast.type.FunType;
import ast.IdNode;
import ast.dec.AssetNode;
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
		System.out.println("PROVA");
		System.out.println(this.entry.getNestinglevel());
		System.out.println(this.nestingLevel);
		for(int i=0; i < (this.nestingLevel- this.entry.getNestinglevel()); i++) {
			
			alcgen += "lw $al $al 0\n";
			//alcgen += "move $al $al\n";
		}
		
		String callcgen ="push $fp\n"+
						paramcgen+
						//"lw $al $fp 0\n"+
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

	private void substituteEnv0(EEnvironment env, EEnvironment env0, EEnvironment env1) {
		//EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		EEntryAsset asset;
		EEntryAsset par;
		ArrayList<AssetNode> parAsset = ((FunType)(this.entry.getType())).getParAsset();
		
		for (int pos = this.idList.size() - 1; pos >= 0; pos--) {
			asset = (EEntryAsset) env.lookUp(this.idList.get(pos).getId());
			par = (EEntryAsset) env0.lookUp(parAsset.get(pos).getId());
			
			par.updateEffectState(asset.getEffectState());
			//System.out.println(par.getEffectState());
			asset.updateEffectState("0");
		}
		
		HashMap<String, EEntry> map = env0.getAllAsset();
		for (String id : map.keySet()) {
			//System.out.println(((EEntryAsset)(map.get(id))).getEffectState());
			if (((EEntryAsset)(map.get(id))).getEffectState() != "1" && ((EEntryAsset)(map.get(id))).getEffectState() != "0") {
				/*System.out.println(((EEntryAsset)(map.get(id))).getEffectState());
				System.out.println(((EEntryAsset)(env.lookUp(id))).getEffectState());*/
				((EEntryAsset)(map.get(id))).updateEffectState(((EEntryAsset)(env.lookUp(id))).getEffectState());
			}
		}
		/*
		for (String id : map.keySet()) {
			System.out.println(id);
			System.out.println(((EEntryAsset)(map.get(id))).getEffectState());
		}
		*/
		return ;
	}
	
	private void calculateEnv1(EEnvironment env, EEnvironment env0, EEnvironment env1) {
		// CONTROLLO LIQUIDITY E AGGIORNAMENTO ENV
		//EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		//EEnvironment env1 = ((EEntryFun)(env.lookUp(this.id))).getEnv1();
		
		int i;
		String[] split;
		HashMap<String, EEntry> subs = env0.getAllAsset();
		HashMap<String, EEntry> map = env1.getAllAsset();
		for (String id : map.keySet()) {
			if (((EEntryAsset)(map.get(id))).getEffectState() != "1" && ((EEntryAsset)(map.get(id))).getEffectState() != "0") {
				
				split = (((EEntryAsset)(map.get(id))).getEffectState()).split(Pattern.quote("+"));
				/*for (String s : split) {
					System.out.println("split");
					System.out.println(s);
				}*/
				
				//split = ((((EEntryAsset)(map.get(id))).getEffectState()).contains("+") ? (((EEntryAsset)(map.get(id))).getEffectState()).split("+") : new String[] {((EEntryAsset)(map.get(id))).getEffectState()});
				
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
		for (String id : map.keySet()) {
			System.out.println(id);
			System.out.println(((EEntryAsset)(map.get(id))).getEffectState());
		}
		*/
		return ;
	}
	
	@Override
	public void analyzeEffect(EEnvironment env) {
		boolean liquidity = true;
		EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		EEnvironment env1 = ((EEntryFun)(env.lookUp(this.id))).getEnv1();
		
		EEnvironment clone0 = env0.clone();
		EEnvironment clone1 = env1.clone();
		substituteEnv0(env, clone0, clone1);
		calculateEnv1(env, clone0, clone1);
		
		//HashMap<String, EEntry> mp = clone0.getAllAsset();
		HashMap<String, EEntry> map = clone1.getAllAsset();
		for (String id : map.keySet()) {
			//System.out.println(id);
			//System.out.println(((EEntryAsset)(map.get(id))).getEffectState());
			if (env.lookUp(id) != null) {
				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map.get(id))).getEffectState());
			}
		}
		/*HashMap<String, EEntry> as = env.getAllAsset();
		for (String id : as.keySet()) {
			System.out.println(id);
			System.out.println(((EEntryAsset)(env.lookUp(id))).getEffectState());
		}*/
		
		HashMap<String, EEntry> envAsset = env.getAllAsset();
		for (String id : envAsset.keySet()) {
			if (clone1.lookUp(id) != null) {
				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map.get(id))).getEffectState());
				//System.out.println(id);
				//System.out.println(((EEntryAsset)(envAsset.get(id))).getEffectState());
			}
		}
		
		ArrayList<AssetNode> parAsset = ((FunType)(this.entry.getType())).getParAsset();
		
		/*for (AssetNode parFor : parAsset) {
			System.out.println("fdfs");
			System.out.println(((EEntryAsset)(clone1.lookUp(parFor.getId()))).getEffectState());
			if (((EEntryAsset)(clone1.lookUp(parFor.getId()))).getEffectState() != "0") {
				liquidity = false;
			}
		}
		
		if (!liquidity) {
			System.out.println(new EffectError(row, column, "Liquidity of function [" + this.id + "] is not respected").toPrint());
			System.exit(0);
		}*/
		
		return ;
	}

}
