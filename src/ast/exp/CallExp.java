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

 // Funzione ausiliaria utile per settare l'ambiente di entrata della funzione, con i valori correnti degli asset
 	private void substituteEnv0(EEnvironment env, EEnvironment env0, EEnvironment env1) {
 		//System.out.println("ENV 0");
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
 		
 		// Aggiorno anche i valori degli asset globali all'interno dell'ambiente di entrata
 		HashMap<String, EEntry> map = env0.getAllAsset();
 		for (String id : map.keySet()) {
 			// Se l'asset che stiamo controllando � globale, allora procedo con l'aggiornametnto dell'effetto
 			if (!formals.contains(id) && !((EEntryAsset)(map.get(id))).getEffectState().equals("1") && !((EEntryAsset)(map.get(id))).getEffectState().equals("0")) {
 				((EEntryAsset)(map.get(id))).updateEffectState(((EEntryAsset)(env.lookUp(id))).getEffectState());
 			}
 		}

 		/*
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
 		*/
 		return ;
 	}
 	
 	// VERIFICARE L'AGGIORNAMENTO DELLE VARIABILI, SOLO QUELLE GLOBALI DOVREBBERO ESSERE AGGIORNATE
 	
 	// Funzione ausiliaria che calcola l'ambiente di uscita partendo dall'ambiente di entrata creato precedentemente
 	private void calculateEnv1(EEnvironment env, EEnvironment env0, EEnvironment env1, ArrayList<String> param) {
 		//System.out.println("ENV 1");
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
 						//System.out.println("res:" + res);
 						while (i < split.length) { 
 							//System.out.println("split" + i +":" +((EEntryAsset)(subs.get(split[i]))).getEffectState());
 							res = EEntryAsset.effectStatePlus(res, ((EEntryAsset)(subs.get(split[i]))).getEffectState());
 							i++;
 						}
 						//System.out.println("res:" + res);
 						
 						((EEntryAsset)(map.get(id))).updateEffectState(res);
 					}
 				}
 			}
 		}
 		
 		/*
 		System.out.println("ENV 1");
 		env1.getSymTable().forEach( hashmap -> {
 			System.out.println();
 			hashmap.forEach( (id, entry) -> {
 				if (entry instanceof EEntryAsset) {
 					System.out.println(id);
 					System.out.println(((EEntryAsset)(entry)).getEffectState());
 				}
 			});
 		});
 		System.out.println("ENV 1");
 		*/
 		
 		return ;
 	}
 	
 	@Override
 	public void analyzeEffect(EEnvironment env) {
 		System.out.println("CALL EXP");
 		EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
 		EEnvironment env1 = ((EEntryFun)(env.lookUp(this.id))).getEnv1();
 				
 		// Lista degli ID dei parametri formali della funzione chiamata
 		ArrayList<String> param = new ArrayList<String>();
 		for (AssetNode an : ((EEntryFun)(env.lookUp(this.id))).getFunNode().getParAdec().getListAdec()) {
 			param.add(an.getId());
 		}
 		
 		EEnvironment clone0 = env0.clone();
 		EEnvironment clone1 = env1.clone();
 		
 		// Calcolo l'ambiente di entrata in base ai valori correnti
 		substituteEnv0(env, clone0, clone1);
 		// Calcolo i valori di uscita degli asset in base all'ambiente di uscita calcolato precedentemente
 		calculateEnv1(env, clone0, clone1, param);
 		
 		/*
 		System.out.println("xyxyxyxyx");
 		clone0.getSymTable().forEach( hashmap -> {
 			System.out.println();
 			
 			hashmap.forEach( (id, entry) -> {
 				System.out.println(id);
 				if (entry instanceof EEntryAsset)
 					System.out.println(((EEntryAsset)(entry)).getEffectState());
 			});
 		});
 		System.out.println("xyxyxyxyx");
 		*/
 		
 		// Aggiorno l'ambiente principale con i valori degli effetti calcolati nel passaggio precedente
 		HashMap<String, EEntry> map = clone1.getAllAsset();
 		for (String id : map.keySet()) {
 			// Se l'asset che stiamo controllando � globale, allora procedo con l'aggiornametnto dell'effetto
 			if (!param.contains(id)) {
 				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map.get(id))).getEffectState());
 			}
 		}

 		/*
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
 		*/
 		
 		return ;
 	}
 	
 	// Analisi della liquidit�
 	@Override
 	public void analyzeLiquidity(EEnvironment env, String f) {
 		System.out.println("CALL EXP");
 		if (this.id.equals(f)) {
 			System.out.println();
			System.out.println("RECURSION");
			System.out.println();
 			// QUI SIAMO NEL CASO DELLA CHIAMATA RICORSIVA
			EEnvironment rec = ((EEntryFun)(env.getAllFun().get(f))).getEnv1();
			// Aggiorno l'ambiente con l'ambiente di entrata della funzione che sto chiamando
			for (String g : env.getAllAsset().keySet()) {
				// Aggiorno gli effetti degli asset globali in base ai valori di entrata calcolati precedentemente
				if (env.getSymTable().get(env.getNestingLevel()).get(g) != null) {
					((EEntryAsset)(env.lookUp(g))).updateEffectState(((EEntryAsset)(rec.lookUp(g))).getEffectState());
					System.out.println("asset:" + g);
					System.out.println("effect:" + ((EEntryAsset)(rec.lookUp(g))).getEffectState());
				}
			}
			System.out.println();
 		}
 		else {
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
 	 		
 	 		// Memorizzo tutti gli asset locali della funzione con il rispettivo stato
			HashMap<String, EEntry> local = env.getSymTable().get(0);
			
			env.exitScope();
			
 	 		// Passo a valutare la funzione che � stata chiamata
 	 		((EEntryFun)(env.lookUp(this.id))).getFunNode().analyzeLiquidity(env);
 	 		
 	 		// Torneo alla funzione che precedentemente ha fatto la call, quindi dovr� riaggiungere gli asset locali precedentemente salvati con i valori precedenti alla call
			env.entryScope();
 	 		local.forEach( (ida, entry) -> {
 	 			env.addDeclaration(ida, entry);
 	 		});
 		}
 		
 		return ;
 	}

	@Override
	public int calculateExp() {
		return 0;
	}

	@Override
	public void analyzeEffectFixPoint(EEnvironment env, EEnvironment gEnv, String f) {
		System.out.println("CALL EXP FIX POINT");
		// Creo la lista di tutti gli asset attuali passati nella chiamata di funzione
		ArrayList<IdNode> list = this.idList;
		
		// Aggiorno gli effetti degli asset attuali che sono passati tramite chiamata riorsiva
		for (int pos = list.size() - 1; pos >= 0; pos--) {
			((EEntryAsset)(env.getAllAsset().get(list.get(pos).getId()))).updateEffectState("0");
		}
		
		// Aggiorno gli effetti degli asset dopo la chiamata ricorsiva
		for (String g : env.getAllAsset().keySet()) {
			// Aggiorno gli effetti dei soli asset globali
			if (gEnv.getSymTable().get(gEnv.getNestingLevel()).get(g) != null) {
				((EEntryAsset)(env.lookUp(g))).updateEffectState(((EEntryAsset)( ((EEntryFun)(gEnv.lookUp(f))).getEnv1().getAllAsset().get(g))).getEffectState());
				if (gEnv.lookUp(g) instanceof EEntryAsset) {
					System.out.println("asset:" + g);
					System.out.println("effect:" + ((EEntryAsset)( ((EEntryFun)(gEnv.lookUp(f))).getEnv1().getAllAsset().get(g) )).getEffectState());
				}
			}
		}
	}
}