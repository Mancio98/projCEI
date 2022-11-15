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

	// Funzione ausiliaria utile per settare l'ambiente di entrata della funzione, con i valori correnti degli asset
	// In questo caso facendo una pre-valutazione delle espressioni passate come asset
	private void substituteEnv0(EEnvironment env, EEnvironment env0, EEnvironment env1) {
		Exp exp;
		EEntryAsset par;
		ArrayList<AssetNode> parAsset = ((FunType)(this.entry.getType())).getParAsset();
		
		for (int pos = this.exp2List.size() - 1; pos >= 0; pos--) {
			exp = this.exp2List.get(pos);
			par = (EEntryAsset) env0.lookUp(parAsset.get(pos).getId());
			
			// Pre-valutazione
			int valExp = exp.calculateExp();
			// Settaggio degli effetti
			if (valExp > 0) {
				par.updateEffectState("1");
			}
			else {
				par.updateEffectState("0");
			}
		}
		return ;
	}
	
	// Funzione ausiliaria che calcola l'ambiente di uscita partendo dall'ambiente di entrata creato precedentemente
	private void calculateEnv1(EEnvironment env, EEnvironment env0, EEnvironment env1) {
		int i;
		String[] split;
		HashMap<String, EEntry> subs = env0.getAllAsset();
		HashMap<String, EEntry> map = env1.getAllAsset();
		for (String id : map.keySet()) {
			// Controllare se l'effetto dipende da dei termini oppure se � gi� 0 o 1
			if (!((EEntryAsset)(map.get(id))).getEffectState().equals("1") && !((EEntryAsset)(map.get(id))).getEffectState().equals("0")) {
				
				// Se dipende da dei termini allora li sostituiamo con il valore corretto e facciamo la somma astratta tra di essi
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
		
		// Calcolo l'ambiente di entrata in base ai valori correnti
		substituteEnv0(env, l0, l1);
		// Calcolo i valori di uscita degli asset in base all'ambiente di uscita calcolato precedentemente
		calculateEnv1(env, l0, l1);
		
		// Aggiorno l'ambiente principale con i valori degli effetti calcolati nel passaggio precedente
		HashMap<String, EEntry> map = l1.getAllAsset();
		for (String id : map.keySet()) {
			// Controllo per gli asset globali
			if (env.lookUp(id) != null) {
				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map.get(id))).getEffectState());
			}
		}
		
		// Controllo se tutti i valori degli asset sono pari a 0
		for (String as : env.getAllAsset().keySet()) {
			if (!((EEntryAsset)(env.lookUp(as))).getEffectState().equals("0")) {
				liq.add(as);
			}
		}
		
		// Nel caso di almeno un asset globale che non abbia valore 0, allora il programma non � liquido
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
	
	// FORSE CONVIENE FARE ENTRYSCOPE E POI AGGIUNGERE GLI ASSET CHE VENGONO CREATI NELLA FUNZIONE CHIAMATA
	// Analisi della liquidit�
	public void analyzeLiquidity(EEnvironment env) {
		System.out.println("INIT CALL");
		Exp exp;
		EEnvironment env0 = ((EEntryFun)(env.lookUp(this.id))).getEnv0();
		
		/*
		System.out.println("uidity");
		env.getSymTable().forEach( hashmap -> {
			System.out.println();
			hashmap.forEach( (id, entry) -> {
				if (entry instanceof EEntryAsset) {
					System.out.println(id);
					System.out.println(((EEntryAsset)(entry)).getEffectState());
				}
			});
		});
		*/
		
		AdecNode adec = ((EEntryFun)(env.lookUp(this.id))).getFunNode().getParAdec();
		for (int pos = adec.getListAdec().size() - 1; pos >= 0; pos--) {
			AssetNode a = adec.getListAdec().get(pos);
			exp = this.exp2List.get(pos);
			
			// Pre-valutazione
			int valExp = exp.calculateExp();
			if (valExp > 0) {
				((EEntryAsset)(env0.lookUp(a.getId()))).updateEffectState("1");
			}
			else if(valExp == 0) {
				((EEntryAsset)(env0.lookUp(a.getId()))).updateEffectState("0");
			}
			else {
				// Il caso minore di 0 non dovrebbe mai accadere
				System.out.println(new EffectError(row, column, "Passing negative value with move is not possibile").toPrint());
				System.exit(0);
			}
		}
		
		/*
		System.out.println("uidity");
		env.getSymTable().forEach( hashmap -> {
			System.out.println();
			hashmap.forEach( (id, entry) -> {
				if (entry instanceof EEntryAsset) {
					System.out.println(id);
					System.out.println(((EEntryAsset)(entry)).getEffectState());
				}
			});
		});
		*/
		
		// Passo a valutare la funzione che � stata chiamata
		((EEntryFun)(env.lookUp(this.id))).getFunNode().analyzeLiquidity(env);
		
		return ;
	}
}
