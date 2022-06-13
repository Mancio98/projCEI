package ast.exp;

import ast.IdNode;
import ast.dec.AdecNode;
import ast.dec.AssetNode;
import ast.type.FunType;
import ast.type.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import util.EEntry;
import util.EEntryAsset;
import util.EEntryFun;
import util.EEnvironment;
import util.STEnvironment;
import util.STentry;
import util.SemanticError;
import util.TypeError;
import util.STEnvironment.UndeclaredIdException;

//Used for expression of type "call" 
public class CallExp extends Exp {

	private String id;
	private ArrayList<Exp> expList;
	private ArrayList<IdNode> idList;
	private STentry entry;
	private int nestingLevel;

    public CallExp(int row, int column, String idFun, ArrayList<Exp> exp, ArrayList<IdNode> id) {
    	super(row, column);
		this.id = idFun;
		this.expList = exp;
		this.idList = id;
	}

	public String getId() {
		return this.id;
	}
	
	public ArrayList<IdNode> getIdList() {
		return this.idList;
	}
	
	/*public Exp getChild() {
		return this.child;
	}*/
    
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

    @Override
	public Type typeCheck() {
        if (!(this.entry.getType() instanceof FunType)) {
            System.out.println(new TypeError(super.row, super.column,
                    			"[" + this.id + "] is not a function").toPrint());
            System.exit(0);
        }

        FunType typeFun = ((FunType) (this.entry.getType()));
        
        if ((this.expList.size() != typeFun.getParTypes().size()) && (this.idList.size() != typeFun.getParATypes().size())) {
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

	private ArrayList<String> substituteEnv0(EEnvironment env, EEnvironment env0, EEnvironment env1) {
		EEntryAsset asset;
		EEntryAsset par;
		ArrayList<AssetNode> parAsset = ((FunType)(this.entry.getType())).getParAsset();
		ArrayList<String> already = new ArrayList<String>();
		
		for (int pos = this.idList.size() - 1; pos >= 0; pos--) {
			asset = (EEntryAsset) env.lookUp(this.idList.get(pos).getId());
			par = (EEntryAsset) env0.lookUp(parAsset.get(pos).getId());
			already.add(parAsset.get(pos).getId());
			par.updateEffectState(asset.getEffectState());
			asset.updateEffectState("0");	
		}
		
		for (int pos = this.idList.size() - 1; pos >= 0; pos--) {
			asset = (EEntryAsset) env.lookUp(this.idList.get(pos).getId());
			par = (EEntryAsset) env0.lookUp(parAsset.get(pos).getId());
		}
		
		
		HashMap<String, EEntry> map = env0.getAllAsset();
		for (String id : map.keySet()) {			
			if (!already.contains(id) && !((EEntryAsset)(map.get(id))).getEffectState().equals("1") && !((EEntryAsset)(map.get(id))).getEffectState().equals("0")) {
				((EEntryAsset)(map.get(id))).updateEffectState(((EEntryAsset)(env.lookUp(id))).getEffectState());
			}
		}
		return already;
	}
	
	private void calculateEnv1(EEnvironment env, EEnvironment env0, EEnvironment env1) {
		int i;
		String[] split;
		HashMap<String, EEntry> subs = env0.getAllAsset();
		HashMap<String, EEntry> map = env1.getAllAsset();
		for (String id : map.keySet()) {
			if (!((EEntryAsset)(map.get(id))).getEffectState().equals("1") && !((EEntryAsset)(map.get(id))).getEffectState().equals("0")) {
				split = (((EEntryAsset)(map.get(id))).getEffectState()).split(Pattern.quote("+"));
								
				i = 1;
				
				if (!split[0].equals("0") && !split[0].equals("1")) {
					String res = ((EEntryAsset)(subs.get(split[0]))).getEffectState();
					
					while (i < split.length) { 
						EEntryAsset.effectStatePlus(res, ((EEntryAsset)(subs.get(split[i]))).getEffectState());
						i++;
					}
					
					((EEntryAsset)(map.get(id))).updateEffectState(res);
				}
			}
		}

		return ;
	}
	
	@Override
	public void analyzeEffect(EEnvironment env) {
		EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		EEnvironment env1 = ((EEntryFun)(env.lookUp(this.id))).getEnv1();
		
		EEnvironment clone0 = env0.clone();
		EEnvironment clone1 = env1.clone();
		
		ArrayList<String> already = substituteEnv0(env, clone0, clone1);
		calculateEnv1(env, clone0, clone1);
		
		HashMap<String, EEntry> map = clone1.getAllAsset();
		for (String id : map.keySet()) {
			if (!already.contains(id)) {
				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map.get(id))).getEffectState());
			}
		}

		return ;
	}
	
	public void analyzeEffectFixPoint(EEnvironment env0, EEnvironment env1, EEnvironment env) {
		
		return ;
	}
	
	@Override
	public void analyzeLiquidity(EEnvironment env) {
		IdNode id;
		EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		
		AdecNode adec = ((EEntryFun)(env.lookUp(this.id))).getFunNode().getParAdec();
		for (int pos = adec.getListAdec().size() - 1; pos >= 0; pos--) {
			AssetNode a = adec.getListAdec().get(pos);
			id = this.idList.get(pos);
			
			((EEntryAsset)(env0.lookUp(a.getId()))).updateEffectState(((EEntryAsset)(env.lookUp(id.getId()))).getEffectState());
			((EEntryAsset)(env.lookUp(id.getId()))).updateEffectState("0");
		
		}
		
		//env.exitScope();
		((EEntryFun)(env.lookUp(this.id))).getFunNode().analyzeLiquidity(env);
	}

	@Override
	public int calculateExp() {
		return 0;
	}


	
}
