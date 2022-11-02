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

	// Funzione ausiliaria utile per settare l'ambiente di entrata della funzione, con i valori correnti degli asset
	private ArrayList<String> substituteEnv0(EEnvironment env, EEnvironment env0, EEnvironment env1) {
		System.out.println("ENV 0");
		EEntryAsset asset;
		EEntryAsset par;
		ArrayList<AssetNode> parAsset = ((FunType)(this.entry.getType())).getParAsset();
		// Lista degli ID dei parametri formali della funzione chiamata
		ArrayList<String> formals = new ArrayList<String>();
		
		// Valuto dall'ultimo al primo tutti gli asset passati come parametri attuali alla funzione
		for (int pos = this.idList.size() - 1; pos >= 0; pos--) {
			asset = (EEntryAsset) env.lookUp(this.idList.get(pos).getId());
			par = (EEntryAsset) env0.lookUp(parAsset.get(pos).getId());
			formals.add(parAsset.get(pos).getId());
			
			// I parametri formali della funzione vengono riempiti con il valore dei rispettivi parametri attuali, i quali verrano svuotati mano a mano
			par.updateEffectState(asset.getEffectState());
			asset.updateEffectState("0");	
		}
		
		/*
		for (int pos = this.idList.size() - 1; pos >= 0; pos--) {
			asset = (EEntryAsset) env.lookUp(this.idList.get(pos).getId());
			par = (EEntryAsset) env0.lookUp(parAsset.get(pos).getId());
		}
		*/
		
		// Aggiorno anche i valori degli asset globali all'interno dell'ambiente di entrata
		HashMap<String, EEntry> map = env0.getAllAsset();
		for (String id : map.keySet()) {	
			if (!formals.contains(id) && !((EEntryAsset)(map.get(id))).getEffectState().equals("1") && !((EEntryAsset)(map.get(id))).getEffectState().equals("0")) {
				((EEntryAsset)(map.get(id))).updateEffectState(((EEntryAsset)(env.lookUp(id))).getEffectState());
			}
		}

		env.getSymTable().forEach( hashmap -> {
			System.out.println();
			hashmap.forEach( (id, entry) -> {
				if (entry instanceof EEntryAsset) {
					System.out.println(id);
					System.out.println(((EEntryAsset)(entry)).getEffectState());
				}
			});
		});
		
		System.out.println("ENV 0");
		return formals;
	}
	
	// VERIFICARE L'AGGIORNAMENTO DELLE VARIABILI, SOLO QUELLE GLOBALI DOVREBBERO ESSERE AGGIORNATE
	
	// Funzione ausiliaria che calcola l'ambiente di uscita partendo dall'ambiente di entrata creato precedentemente
	private void calculateEnv1(EEnvironment env, EEnvironment env0, EEnvironment env1, ArrayList<String> param) {
		System.out.println("ENV 1");
		int i;
		String[] split;
		HashMap<String, EEntry> subs = env0.getAllAsset();
		HashMap<String, EEntry> map = env1.getAllAsset();
		//System.out.println("CALC");
		for (String id : map.keySet()) {
			//System.out.println(env.getSymTable().get(env.getNestingLevel()).get(id));
			// Controllo se lo stato di un asset dipende da dei termini
			if (!param.contains(id)) {
				//System.out.println(id);
				if (!((EEntryAsset)(map.get(id))).getEffectState().equals("1") && !((EEntryAsset)(map.get(id))).getEffectState().equals("0")) {
					split = (((EEntryAsset)(map.get(id))).getEffectState()).split(Pattern.quote("+"));
					//System.out.println(split);
		
					i = 1;
					
					// Se s�, allora sostituisco i termini con il valore corrispondente e faccio la somma astratta tra essi
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
		}
		env1.getSymTable().forEach( hashmap -> {
			System.out.println();
			hashmap.forEach( (id, entry) -> {
				if (entry instanceof EEntryAsset) {
					System.out.println(id);
					System.out.println(((EEntryAsset)(entry)).getEffectState());
				}
			});
		});
		//System.out.println("CALC");
		System.out.println("ENV 1");
		return ;
	}
	
	@Override
	public void analyzeEffect(EEnvironment env) {
		EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		EEnvironment env1 = ((EEntryFun)(env.lookUp(this.id))).getEnv1();
		
		ArrayList<String> param = new ArrayList<String>();
		for (AssetNode an : ((EEntryFun)(env.lookUp(this.id))).getFunNode().getParAdec().getListAdec()) {
			param.add(an.getId());
		}
		
		EEnvironment clone0 = env0.clone();
		EEnvironment clone1 = env1.clone();
		/*
		System.out.println("BOH");
		env.getSymTable().forEach( hashmap -> {
			System.out.println();
			hashmap.forEach( (id, entry) -> {
				System.out.println(id);
			});
		});
		System.out.println("BOH");
		*/
		// Aggiorno gli ambienti con i valori correnti
		ArrayList<String> formals = substituteEnv0(env, clone0, clone1);
		calculateEnv1(env, clone0, clone1, param);
		
		// Aggiorno l'ambiente principale con i valori degli effetti calcolati nel passaggio precedente
		HashMap<String, EEntry> map = clone1.getAllAsset();
		for (String id : map.keySet()) {
			if (!param.contains(id)) {
				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map.get(id))).getEffectState());
			}
		}

		System.out.println("FUUUUUUUUUUUUUUUUUUUUUN");
		env.getSymTable().forEach( hashmap -> {
			System.out.println();
			
			hashmap.forEach( (id, entry) -> {
				System.out.println(id);
				if (entry instanceof EEntryAsset)
					System.out.println(((EEntryAsset)(entry)).getEffectState());
			});
		});
		System.out.println("FUUUUUUUUUUUUUUUUUUUUUN");
		
		return ;
	}
	
	public void analyzeEffectFixPoint(EEnvironment env0, EEnvironment env1, EEnvironment env) {
		
		return ;
	}
	
	// Analisi della liquidit�
	@Override
	public void analyzeLiquidity(EEnvironment env) {
		IdNode id;
		EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		
		// Valuto dall'ultimo al primo tutti gli asset passati come parametri attuali alla funzione
		AdecNode adec = ((EEntryFun)(env.lookUp(this.id))).getFunNode().getParAdec();
		for (int pos = adec.getListAdec().size() - 1; pos >= 0; pos--) {
			AssetNode a = adec.getListAdec().get(pos);
			id = this.idList.get(pos);
			
			// I parametri formali della funzione vengono riempiti con il valore dei rispettivi parametri attuali, i quali verrano svuotati mano a mano
			((EEntryAsset)(env0.lookUp(a.getId()))).updateEffectState(((EEntryAsset)(env.lookUp(id.getId()))).getEffectState());
			((EEntryAsset)(env.lookUp(id.getId()))).updateEffectState("0");
		
		}
		
		env.exitScope();
		// Passo a valutare la funzione che � stata chiamata
		((EEntryFun)(env.lookUp(this.id))).getFunNode().analyzeLiquidity(env);
	}
}

