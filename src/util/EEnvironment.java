package util;

import java.util.ArrayList;
import java.util.HashMap;
import ast.dec.FunNode;

public class EEnvironment extends Environment<EEntry> {
	
	public enum EffectState {
			E,
			F
	}
	
	public EEnvironment() {
		super();
	}
	
	//Add new HashMap when new scope is entered
	public void entryScope() {
		super.entryScope();
	}
	
	//Remove current HashMap of the scope when scope is left
	public void exitScope() {
		super.exitScope();
	}
	
	public void addDeclarationAsset(String id, String state) {
		symTable.get(0).put(id, new EEntryAsset(nestingLevel, state));
	}
	
	public void addDeclarationFun(String id, EEnvironment env0, EEnvironment env1) {
		symTable.get(0).put(id, new EEntryFun(nestingLevel, env0, env1));
	}
	
	public void addDeclarationFun(String id, EEnvironment env0, EEnvironment env1, FunNode fun) {
		symTable.get(0).put(id, new EEntryFun(nestingLevel, env0, env1, fun));
	}
	
	public void addDeclaration(String id, EEntry entry) {
		symTable.get(0).put(id, entry);
	}
	
	public EEntry lookUp(String id) {
		EEntry entry = null;
		boolean foundId = false;
		
		int i = 0;
		while(i < symTable.size() && !foundId) {
			entry = symTable.get(i).get(id);
			if (entry != null)
				foundId = true;
			i++;
		}
		
		return entry;	
	}
	
	public int getNestingLevel(){
		return super.getNestingLevel();
	}
	
	public ArrayList<HashMap<String, EEntry>> getSymTable(){
		return super.getSymTable();
	}
	// VERIFICARE DOVE NON VIENE FATTO BENE IL CLONE
		// Crea una copia dell'ambiente chiamante
		public EEnvironment clone() {
			EEnvironment env = new EEnvironment();
			symTable.forEach( hashmap -> {
				env.symTable.add(new HashMap<String, EEntry>()); 
				env.nestingLevel++;
				//env.symTable.add(0, new HashMap<String, EEntry>());
				//env.nestingLevel++;
				hashmap.forEach( (id, entry) -> {
					if (entry instanceof EEntryAsset) {
						env.symTable.get(env.nestingLevel).put(id, new EEntryAsset(nestingLevel, ((EEntryAsset)(entry)).getEffectState()));
						//env.addDeclarationAsset(id, ((EEntryAsset)(entry)).getEffectState());
					}
					else {
						env.symTable.get(env.nestingLevel).put(id, new EEntryFun(nestingLevel, ((EEntryFun)(entry)).getEnv0().clone(), ((EEntryFun)(entry)).getEnv1().clone()));
						//env.addDeclarationFun(id, ((EEntryFun)(entry)).getEnv0().clone(), ((EEntryFun)(entry)).getEnv1().clone());
					}
				});
			});
			/*
			System.out.println("CLONEEEEEE");
			env.symTable.forEach( hashmap -> {
				System.out.println();
				hashmap.forEach( (id, entry) -> {
					System.out.println(id);
				});
			});
			
			System.out.println("TOOOOOOO");
			symTable.forEach( hashmap -> {
				System.out.println();
				hashmap.forEach( (id, entry) -> {
					System.out.println(id);
				});
			});
			*/
			return env;
		}
		
		// Restituisce tutti gli asset dichiarati nell'ambiente chiamante
		public HashMap<String, EEntry> getAllAsset() {
			HashMap<String, EEntry> map = new HashMap<String, EEntry>();
			symTable.forEach(scope -> { scope.forEach((id, entry) -> { if (entry instanceof EEntryAsset) map.put(id, entry); }); });
			return map;
		}
		
		// Restituisce tutte le funzioni dichiarate nell'ambiente chiamante
		public HashMap<String, EEntry> getAllFun() {
			HashMap<String, EEntry> map = new HashMap<String, EEntry>();
			symTable.forEach(scope -> { scope.forEach((id, entry) -> { if (entry instanceof EEntryFun) map.put(id, entry); }); });
			return map;
		}
		
		// Modifica l'ambiente chiamante, quando nell'ambiente passato troviamo asset che hanno un effetto maggiore (0 < 1)
		public void maxModifyEnv(EEnvironment tmpEnv) {
			HashMap<String, EEntry> map = tmpEnv.getAllAsset();
			map.forEach((id, entry) -> {((EEntryAsset)(this.lookUp(id))).updateEffectState(EEntryAsset.effectStateMax(((EEntryAsset)(this.lookUp(id))).getEffectState(), ((EEntryAsset)(tmpEnv.lookUp(id))).getEffectState())); });
		}
		
		// Uguaglianza tra ambienti, fatta asset per asset
		public static boolean environmentEquality(EEnvironment env1, EEnvironment env2) {		
			for (String id : env1.getAllAsset().keySet()) {
				if (env2.lookUp(id) == null || !((EEntryAsset)(env2.lookUp(id))).getEffectState().equals(((EEntryAsset)(env1.lookUp(id))).getEffectState())) {
					return false;
				}
			}
			return true;
		}
	}