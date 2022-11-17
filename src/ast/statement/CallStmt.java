package ast.statement;

import java.util.ArrayList;
import java.util.HashMap;
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
import ast.dec.AdecNode;
import ast.dec.AssetNode;
import util.STentry;
import util.TypeError;

//Used to the management of function's call
public class CallStmt extends Statement {
	
	private String id;
	private ArrayList<Exp> expList;
	private ArrayList<IdNode> idList;
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
	
	public ArrayList<IdNode> getIdList() {
		return this.idList;
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
		String emptyAsset = "";
		
		for(int i = this.idList.size()-1; i>= 0; i--) {
			
			paramcgen += this.idList.get(i).codeGeneration();
			paramcgen += "push $a0\n";
			emptyAsset += this.idList.get(i).reachId()+
					"li $t1 0\n"+
					"sw $t1 $al "+this.idList.get(i).getSTentry().getOffset()+"\n";
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
						emptyAsset+
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

    // Auxiliary function for setting the function entry environment with current asset value
	private void substituteEnv0(EEnvironment env, EEnvironment env0, EEnvironment env1) {
		EEntryAsset asset;
		EEntryAsset par;
		ArrayList<AssetNode> parAsset = ((FunType)(this.entry.getType())).getParAsset();
		// List of ID of formal parameters of called function
		ArrayList<String> formals = new ArrayList<String>();
		
		
		// We evaluate from the last to the first all the assets passed as current parameters of function
		for (int pos = this.idList.size() - 1; pos >= 0; pos--) {
			asset = (EEntryAsset) env.lookUp(this.idList.get(pos).getId());
			par = (EEntryAsset) env0.lookUp(parAsset.get(pos).getId());
			formals.add(parAsset.get(pos).getId());
			
 			// Formal parameters of function are filled with value of current parameters,which will be emptied gradually
			par.updateEffectState(asset.getEffectState());
			asset.updateEffectState("0");	
		}
		
 		// Updating of global asset value inside the entry environment
		HashMap<String, EEntry> map = env0.getAllAsset();
		for (String id : map.keySet()) {
 			// If the asset that we are checking is a global asset, then we update the effect
			if (!formals.contains(id) && !((EEntryAsset)(map.get(id))).getEffectState().equals("1") && !((EEntryAsset)(map.get(id))).getEffectState().equals("0")) {
				((EEntryAsset)(map.get(id))).updateEffectState(((EEntryAsset)(env.lookUp(id))).getEffectState());
			}
		}
		return ;
	}
	
 	// Auxiliary function that calculates the exit environment from the previously created entry environment
	private void calculateEnv1(EEnvironment env, EEnvironment env0, EEnvironment env1, ArrayList<String> param) {
		int i;
		String[] split;
		HashMap<String, EEntry> subs = env0.getAllAsset();
		HashMap<String, EEntry> map = env1.getAllAsset();
		for (String id : map.keySet()) {
 			// Checking if the asset state depends on other terms
			if (!param.contains(id)) {
				if (!((EEntryAsset)(map.get(id))).getEffectState().equals("1") && !((EEntryAsset)(map.get(id))).getEffectState().equals("0")) {
					split = (((EEntryAsset)(map.get(id))).getEffectState()).split(Pattern.quote("+"));
		
					i = 1;
					
 					// If it depends on other terms, we substitute the terms with the current value and we do the abstract sum
					if (!split[0].equals("0") && !split[0].equals("1")) {
						String res = ((EEntryAsset)(subs.get(split[0]))).getEffectState();
						while (i < split.length) {
							res = EEntryAsset.effectStatePlus(res, ((EEntryAsset)(subs.get(split[i]))).getEffectState());
							i++;
						}
						((EEntryAsset)(map.get(id))).updateEffectState(res);
					}
				}
			}
		}
		return ;
	}
	
	@Override
	public void analyzeEffect(EEnvironment env) {
		EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		EEnvironment env1 = ((EEntryFun)(env.lookUp(this.id))).getEnv1();
				
 		// ID list of the formal parameters of called function
		ArrayList<String> param = new ArrayList<String>();
		for (AssetNode an : ((EEntryFun)(env.lookUp(this.id))).getFunNode().getParAdec().getListAdec()) {
			param.add(an.getId());
		}
		
		EEnvironment clone0 = env0.clone();
		EEnvironment clone1 = env1.clone();
		
 		// We calculate the entry environment based on the current values
 		substituteEnv0(env, clone0, clone1);
 		// We calculate the exit environment based on the previously created entry environment
 		calculateEnv1(env, clone0, clone1, param);
		
 		// We update the main environment with the effect value previously calculated
		HashMap<String, EEntry> map = clone1.getAllAsset();
		for (String id : map.keySet()) {
 			// If the asset that we are checking is global, than we update the effect
			if (!param.contains(id)) {
				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map.get(id))).getEffectState());
			}
		}
		return ;
	}
	
	@Override
	public void analyzeEffectFixPoint(EEnvironment env, EEnvironment gEnv, String f) {
		// We create the list of all the current asset passed inside the call of the function
		ArrayList<IdNode> list = this.idList;
		
		// We update the current asset effect that are passed in the recursive call
		for (int pos = list.size() - 1; pos >= 0; pos--) {
			((EEntryAsset)(env.getAllAsset().get(list.get(pos).getId()))).updateEffectState("0");
		}
		
		// We update the asset effect after the recursive call
		for (String g : env.getAllAsset().keySet()) {
			// We update the effect of only global asset
			if (gEnv.getSymTable().get(gEnv.getNestingLevel()).get(g) != null) {
				((EEntryAsset)(env.lookUp(g))).updateEffectState(((EEntryAsset)( ((EEntryFun)(gEnv.lookUp(f))).getEnv1().getAllAsset().get(g))).getEffectState());
			}
		}
	}
	
 	// Liquidity analysis
	@Override
	public void analyzeLiquidity(EEnvironment env, String f) {
		if (this.id.equals(f)) {
 			// Recursive case
			EEnvironment rec = ((EEntryFun)(env.getAllFun().get(f))).getEnv1();
			// We update the environment with the entry environment of the function that we are going to call
			for (String g : env.getAllAsset().keySet()) {
				// We update the global assets effect based on the previously calculated entry values
				if (env.getSymTable().get(env.getNestingLevel()).get(g) != null) {
					((EEntryAsset)(env.lookUp(g))).updateEffectState(((EEntryAsset)(rec.lookUp(g))).getEffectState());
					}
			}
		}
		else {			
			IdNode id;
			EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
			
 	 		// We evaluate from the last to the first assets passed as function current parameters
			AdecNode adec = ((EEntryFun)(env.lookUp(this.id))).getFunNode().getParAdec();
			for (int pos = adec.getListAdec().size() - 1; pos >= 0; pos--) {
				AssetNode a = adec.getListAdec().get(pos);
				id = this.idList.get(pos);

 	 			// The function formal parameters are filled with the value of current parameters, which will be emptied gradually 
				((EEntryAsset)(env0.lookUp(a.getId()))).updateEffectState(((EEntryAsset)(env.lookUp(id.getId()))).getEffectState());
				((EEntryAsset)(env.lookUp(id.getId()))).updateEffectState("0");
			
			}
			
 	 		// We save all the function local asset with the state
			HashMap<String, EEntry> local = env.getSymTable().get(0);
			
			env.exitScope();

			//We evaluate the called function
			((EEntryFun)(env.lookUp(this.id))).getFunNode().analyzeLiquidity(env);
			
 	 		// We go back to the function that previously made the call, so we have to add again the local asset previously saved with the value before the call
			env.entryScope();
 	 		local.forEach( (ida, entry) -> {
 	 			env.addDeclaration(ida, entry);
 	 		});
 	 		

		}
		
		return ;
	}
}

