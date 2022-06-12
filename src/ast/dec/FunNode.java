package ast.dec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import ast.IdNode;
import ast.Node;
import util.EEntry;
import util.EEntryAsset;
import util.EEntryFun;
import util.EEnvironment;
import util.EffectError;
import util.Environment;
import util.STEnvironment.DuplicateEntryException;
import util.STEnvironment;
import util.SemanticError;
import util.TypeError;
import ast.statement.CallStmt;
import ast.statement.IteStmt;
import ast.statement.ReturnStmt;
import ast.statement.Statement;
import ast.type.Type;
import ast.type.AssetType;
import ast.type.BoolType;
import ast.type.VoidType;
import ast.type.FunType;
import ast.type.IntType;

//Used to the management of function's declaration
public class FunNode extends Node {
	private Type type;
	private String id;
	private DecNode parDec;
	private AdecNode parAdec;
	private ArrayList<DecNode> decList;
	private ArrayList<Statement> statementList;
	private FunType funType;

	public FunNode(int row, int column, Type type, String id) {
		super(row, column);
		this.type = type;
		this.id = id;
	}
	
	public void addPar(DecNode dec, AdecNode adec) {
		if(dec == null)
			this.parDec = new DecNode(new ArrayList<VarNode>());
		else
			this.parDec = dec;
		
		if(adec == null)
			this.parAdec = new AdecNode(new ArrayList<AssetNode>());
		else
			this.parAdec = adec;
		
		
		ArrayList<Type> parTypes = new ArrayList<Type>();
		
		for(VarNode n : this.parDec.getListDec()) {	
			parTypes.add(n.getType());
		}
			
		ArrayList<Type> parATypes = new ArrayList<Type>();
			
		for(AssetNode n : this.parAdec.getListAdec()) {
			parATypes.add(new AssetType());
		}
		
		this.funType = new FunType(parTypes, parATypes, this.type, parAdec.getListAdec());
	}
	
	public void addBody(ArrayList<DecNode> decList, ArrayList<Statement> stmt) {
		this.decList = decList;
		this.statementList = stmt;
	}
	public String getId() {
		return this.id;
	}
	
	public AdecNode getParAdec() {
		return this.parAdec;
	}
	
	@Override
	public String toPrint(String indent) {
		String s = indent + "Function:\n" + indent + "\tFun: " + this.id + " " + this.type.toPrint("") + " " + this.funType.toPrint("");
		String tab = "\t\t";
		
		if (!parDec.getListDec().isEmpty()) {
			s += this.parDec.toPrint(indent + tab);
		}
		
		if (!parAdec.getListAdec().isEmpty()) {
			s += this.parAdec.toPrint(indent + tab);
		}
		
		if (!(parDec.getListDec().isEmpty() && parAdec.getListAdec().isEmpty())) {
			tab += "\t";
		}
		
		for(DecNode d : this.decList) {
			s += d.toPrint(indent + tab);
		}
		
		for(Statement stmt : this.statementList) {
			s += "\n" + stmt.toPrint(indent + tab);
		}
		
		return s;
	}

	@Override
	public Type typeCheck() {
		
		for(DecNode node : this.decList) {
			node.typeCheck();
		}
		
		for(Statement stmt : this.statementList) {
			Type stmtType = stmt.typeCheck();
			
			if (stmtType != null) {
				if (stmt instanceof CallStmt) {
	    			if (!stmtType.isSubtype(new VoidType())) {
	    				System.out.println(new TypeError(stmt.getRow(), stmt.getColumn(),
	    									"Invalid call of function [" + ((CallStmt)stmt).getId() + "]").toPrint());
	    				System.exit(0);
	    			}
	    		}
				else {
					if (!stmtType.isSubtype(this.type)) {
						System.out.println(new TypeError(super.row, super.column, 
								"Return type [" + stmtType.getType() + "] does not match function type [" + this.type.getType() + "]").toPrint());
						System.exit(0);
					}
					else {
						if (this.statementList.indexOf(stmt) != (this.statementList.size() - 1)) {
							System.out.println(new TypeError(super.row, super.column, 
									"Return is not the last statement of the function").toPrint());
							System.exit(0);
						}
					}
				}
			}
		}
		
		return new VoidType();
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		
		ArrayList<SemanticError> semErrors = new  ArrayList<SemanticError>();
		
		try {
			env.addDeclaration(this.id, this.funType);
		}
		catch (DuplicateEntryException e) {
			semErrors.add(new SemanticError(super.row, super.column, "id "+ this.id + " already declared"));
		}
			
		env.entryScope();
			
		semErrors.addAll(this.parDec.checkSemantics(env));
		
		semErrors.addAll(this.parAdec.checkSemantics(env));
			
		for(DecNode n : this.decList) {
			semErrors.addAll(n.checkSemantics(env));
		}
		
		for(Statement stmt : this.statementList) {
			semErrors.addAll(stmt.checkSemantics(env));
		}
			
		env.exitScope();
		
		return semErrors;
	}

	public void analyzeLiquidity(EEnvironment env) {
		String[] split;
		int i;
		
		System.out.println("000000000000000000");
		//EEnvironment env1 = ((EEntryFun)(env.getAllFun().get(this.id))).getEnv1();
		for (String a : env.getAllAsset().keySet()) {
			System.out.println(a);
			System.out.println(((EEntryAsset)(env.getAllAsset().get(a))).getEffectState());
		}
		for (String a : env.getAllFun().keySet()) {
			System.out.println(a);
			EEnvironment e3 = ((EEntryFun)(env.getAllFun().get(a))).getEnv1();
			
			
			for (String aa : e3.getAllAsset().keySet()) {
				System.out.println(aa);
				System.out.println(((EEntryAsset)(e3.getAllAsset().get(aa))).getEffectState());
			}
		}
		System.out.println("000000000000000000");
		
		EEnvironment e0 = ((EEntryFun)(env.getAllFun().get(this.id))).getEnv0();
		EEnvironment env0 = e0.clone();
		for (String g : env.getAllAsset().keySet()) {
			((EEntryAsset)(env0.lookUp(g))).updateEffectState(((EEntryAsset)(env.lookUp(g))).getEffectState());
		}
		
		System.out.println("11111111111111111");
		//EEnvironment env1 = ((EEntryFun)(env.getAllFun().get(this.id))).getEnv1();
		for (String a : env0.getAllAsset().keySet()) {
			System.out.println(a);
			System.out.println(((EEntryAsset)(env0.getAllAsset().get(a))).getEffectState());
		}
		System.out.println("111111111111111");
		
		EEnvironment e1 = ((EEntryFun)(env.getAllFun().get(this.id))).getEnv1();
		EEnvironment env1 = e1.clone();
		
		System.out.println("22222222222222");
		for (String a : env1.getAllAsset().keySet()) {
			System.out.println(a);
			System.out.println(((EEntryAsset)(env1.getAllAsset().get(a))).getEffectState());
		}
		System.out.println("22222222222222");
		for (String a : env1.getAllAsset().keySet()) {
			if (!((EEntryAsset)(env1.lookUp(a))).getEffectState().equals("1") && !((EEntryAsset)(env1.lookUp(a))).getEffectState().equals("0")) {
				split = ((EEntryAsset)(env1.lookUp(a))).getEffectState().split(Pattern.quote("+"));
				
				i = 1;
				if (!split[0].equals("0") && !split[0].equals("1")) {
					String res = ((EEntryAsset)(env0.getAllAsset().get(split[0]))).getEffectState();
					while (!res.equals("1") && !res.equals("0") && i < split.length) { // (((EEntryAsset)(map.get(id))).getEffectState()).length() > 1 && 
						//System.out.println(((EEntryAsset)(subs.get(split[i]))).getEffectState());
						EEntryAsset.effectStatePlus(res, ((EEntryAsset)(env0.getAllAsset().get(split[i]))).getEffectState());
						//EEntryAsset.effectStatePlus(((EEntryAsset)(subs.get(split[i]))).getEffectState(), (((EEntryAsset)(map.get(id))).getEffectState()).substring((((EEntryAsset)(map.get(id))).getEffectState()).indexOf("+"), (((EEntryAsset)(map.get(id))).getEffectState()).length() - 1));
						i++;
					}
					
					System.out.println();
					System.out.println(a);
					System.out.println(res);
					((EEntryAsset)(env1.getAllAsset().get(a))).updateEffectState(res);
				}
			}
			
			//((EEntryAsset)(env0.lookUp(a))).updateEffectState(((EEntryAsset)(env.lookUp(a))).getEffectState());
		}
		
		System.out.println("LLLLLLLLLLLLLLLLLLLL");
		for (String id : env1.getAllAsset().keySet()) {
			System.out.println(id);
			System.out.println(((EEntryAsset)(env1.lookUp(id))).getEffectState());
		}
		System.out.println("LLLLLLLLLLLLLLLLLLLL");
		for (AssetNode an : this.parAdec.getListAdec()) {
			if (!((EEntryAsset)(env1.lookUp(an.getId()))).getEffectState().equals("0")) {
				System.out.println(an.getId());
				System.out.println();
				System.out.println(new EffectError(row, column, "Function [" + this.id + "] is not liquid").toPrint());
				System.exit(0);
			}
		}
		
		for(Statement stmt : this.statementList) {
			
			if (stmt instanceof CallStmt) {
				if (!((CallStmt)(stmt)).getId().equals(this.id)) {
					// MANCA LA CHIAMATA A CALL PER LIQUIDITY
					// PROBLEMA SU AMBIENTE PASSATO ALLA CALL
					// MANCANO I PARAMETRI FORMALI NELL'AMBIENTE PASSATO
					for (String g : env.getAllAsset().keySet()) {
						((EEntryAsset)(env.lookUp(g))).updateEffectState(((EEntryAsset)(env0.lookUp(g))).getEffectState());
					}
					
					
					System.out.println("ENTRYYYYYYYYYYYYYYYYYYYYYY");
					env.entryScope();
					for (String add : env0.getAllAsset().keySet()) {
						if (env.getAllAsset().get(add) == null) {
							System.out.println(add);
							System.out.println(((EEntryAsset)(env0.getAllAsset().get(add))).getEffectState());
							env.addDeclarationAsset(add, ((EEntryAsset)(env0.getAllAsset().get(add))).getEffectState());
						}	
					}
					System.out.println("ENTRYYYYYYYYYYYYYYYYYYYYYY");
					
					((CallStmt)(stmt)).analizeLiquidity(env);
					
					//((EEntryFun)(env.lookUp(((CallStmt)(stmt)).getId()))).getFunNode().analyzeLiquidity(env);
				}
				
			}
			else if (stmt instanceof IteStmt) {
				((IteStmt)(stmt)).analizeLiquidity(env0, env, this.id);
			}
			else {
				stmt.analizeLiquidity(env0);
			}
		}
		
		//env.exitScope();
	}
	
	private boolean isRecursive(Statement stmt) {
		boolean isRec = false;
		
		for(Statement s : ((IteStmt)(stmt)).getThenStmt()) {
			if (s instanceof CallStmt && ((((CallStmt)(s)).getId()).equals(this.id))) {
				isRec = true;
			}
			else if (s instanceof IteStmt) {
				isRec = isRec || isRecursive(s);
			}
		}
		if (!isRec) {
			for(Statement s : ((IteStmt)(stmt)).getElseStmt()) {
				if (s instanceof CallStmt && ((((CallStmt)(s)).getId()).equals(this.id))) {
					isRec = true;
				}
				else if (s instanceof IteStmt) {
					isRec = isRec || isRecursive(s);
				}
			}
		}
		return isRec;
	}
	
	@Override
	public void analizeEffect(EEnvironment env) {
		System.out.println("EFECT FUUUUUUUUUUUUUUUN");
		boolean isRec = false;
		for (Statement stmt : this.statementList) {		
			if (stmt instanceof CallStmt && ((((CallStmt)(stmt)).getId()).equals(this.id))) {
				isRec = true;
			}
			else if (stmt instanceof IteStmt) {
				isRec = isRec || isRecursive(stmt);
			}
		}
		//System.out.println(isRec);
		env.entryScope();
		
		this.parDec.analizeEffect(env);
		this.parAdec.analizeEffect(env);
		for (AssetNode asset : this.parAdec.getListAdec()) {
			((EEntryAsset)(env.lookUp(asset.getId()))).updateEffectState(asset.getId());
		}
		
		for(DecNode n : this.decList) {
			n.analizeEffect(env);
		}
		
		//EEnvironment env0 = env.clone();
		//EEnvironment env1 = env.clone();
		
		EEnvironment envFun = new EEnvironment();
		EEnvironment env1 = new EEnvironment();
		
		envFun.entryScope();
		env1.entryScope();
		
		HashMap<String, EEntry> mapFun = env.getAllFun();
		HashMap<String, EEntry> map10 = env.getAllFun();
		
		mapFun.forEach((id, entry) -> { envFun.addDeclaration(id, entry); });
		map10.forEach((id, entry) -> { env1.addDeclaration(id, entry); });
		
		envFun.entryScope();
		env1.entryScope();
		
		HashMap<String, EEntry> mapAsset = env.getAllAsset();
		HashMap<String, EEntry> mapAsset1 = env.getAllAsset();
		
		mapAsset.forEach((id, entry) -> { envFun.addDeclarationAsset(id, id); });
		mapAsset1.forEach((id, entry) -> { env1.addDeclarationAsset(id, id); });
		
		if (!isRec) {
			System.out.println("NOT REC");
			for(Statement stmt : this.statementList) {
				stmt.analizeEffect(env1);
			}
			
			System.out.println("ENV0");
			HashMap<String, EEntry> map3 = envFun.getAllAsset();
			for (String id : map3.keySet()) {
				System.out.println(id);
				System.out.println(((EEntryAsset)(map3.get(id))).getEffectState());
				
			}
			System.out.println("ENV0");
			
			System.out.println("ENV1");
			HashMap<String, EEntry> map2 = env1.getAllAsset();
			for (String id : map2.keySet()) {
				System.out.println(id);
				System.out.println(((EEntryAsset)(map2.get(id))).getEffectState());
			}
			System.out.println("ENV1");

			
			env.exitScope();
			env.addDeclarationFun(id, envFun, env1, this);
		}
		else {
			
			for (String asst : envFun.getAllAsset().keySet()) {
				((EEntryAsset)(envFun.getAllAsset().get(asst))).updateEffectState("1");
				((EEntryAsset)(env1.getAllAsset().get(asst))).updateEffectState("1");
			}
			
			env.exitScope();
			env.addDeclarationFun(id, envFun, env1, this);
			analyzeEffectFixPoint(envFun, env1.clone(), env);
		}
		System.out.println("EFECT FUUUUUUUUUUUUUUUN");
		return ;
	}
	
	
	private void analyzeEffectFixPoint(EEnvironment env0, EEnvironment env1, EEnvironment env) {
		EEnvironment newEnv0 = new EEnvironment();
		newEnv0.entryScope();
		EEnvironment tmpEnv = env.clone();
		EEnvironment tmpEnv0 = ((EEntryFun)(tmpEnv.lookUp(this.id))).getEnv0();
		System.out.println("FIXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		for(Statement stmt : this.statementList) {
			
			// FARE CASO CallStmt DOVE NON CORRISPONDE IL NOME DELLA FUNZIONE
			// UGUALE A QUELLO FATTO MA NON VA CREATO IL NUOVO ENV
			
			if (stmt instanceof CallStmt && ((CallStmt)(stmt)).getId().equals(this.id)) {
				ArrayList<AssetNode> aList = this.parAdec.getListAdec();
				ArrayList<IdNode> list = ((CallStmt)(stmt)).getIdList();
				
				System.out.println("NEW ENV 0");
				for (int pos = list.size() - 1; pos >= 0; pos--) {
					System.out.println(aList.get(pos).getId());
					System.out.println(((EEntryAsset)(tmpEnv0.getAllAsset().get(list.get(pos).getId()))).getEffectState());
					newEnv0.addDeclarationAsset(aList.get(pos).getId(), ((EEntryAsset)(tmpEnv0.getAllAsset().get(list.get(pos).getId()))).getEffectState());
					((EEntryAsset)(tmpEnv0.getAllAsset().get(list.get(pos).getId()))).updateEffectState("0");
					
				}
				for (String a0 : tmpEnv0.getAllAsset().keySet()) {
					if (newEnv0.lookUp(a0) == null) {
						newEnv0.addDeclarationAsset(a0, ((EEntryAsset)(tmpEnv0.getAllAsset().get(a0))).getEffectState());
						System.out.println(a0);
						System.out.println(((EEntryAsset)(tmpEnv0.getAllAsset().get(a0))).getEffectState());
					}
					
				}
				System.out.println("NEW ENV 0");
				
				HashMap<String, EEntry> map = env.getAllAsset();
				for (String id : map.keySet()) {
					((EEntryAsset)(map.get(id))).updateEffectState(((EEntryAsset)(env1.getAllAsset().get(id))).getEffectState());
					System.out.println(id);
					System.out.println(((EEntryAsset)(env1.getAllAsset().get(id))).getEffectState());
				}
				
				System.out.println("POST REC CALL");
				for (int pos = list.size() - 1; pos >= 0; pos--) {
					System.out.println(list.get(pos).getId());
					System.out.println(((EEntryAsset)( env1.lookUp( ((EEntryFun)(env.lookUp(this.id))).getFunNode().getParAdec().getListAdec().get(pos).getId() ))).getEffectState());
					
					// ((EEntryAsset)(tmpEnv0.getAllAsset().get(list.get(pos).getId()))).updateEffectState( ((EEntryAsset)(env1.getAllAsset().get(id))).getEffectState());
					((EEntryAsset)(tmpEnv0.getAllAsset().get(list.get(pos).getId()))).updateEffectState( ((EEntryAsset)( env1.lookUp( ((EEntryFun)(env.lookUp(this.id))).getFunNode().getParAdec().getListAdec().get(pos).getId() ))).getEffectState() );
					
					
				}

				System.out.println("POST REC CALL");

			}
			else {
				if (stmt instanceof IteStmt) {
					((IteStmt)(stmt)).analizeEffectFixPoint(tmpEnv0, env, this.id);
				}
				else {
					stmt.analizeEffect(tmpEnv0);
				}

			}
		}
		
		System.out.println("oooooooooooooooooooooooooooooooo");
	    if (EEnvironment.environmentEquality(env1, tmpEnv0)) {
	    	// PROBABILMENTE VA SCRITTO DI NUOVO env0 DELLA FUNZIONE METTENDO TUTTI GLI EFFETTI COME TERMINI

	        System.out.println("set function");
	        for (String c0 : env1.getAllAsset().keySet()) {
				System.out.println(c0);
				System.out.println(((EEntryAsset)(env1.getAllAsset().get(c0))).getEffectState());
	        }
	        System.out.println("set function");
	        return ;
	    }
	    System.out.println("oooooooooooooooooooooooooooooooo");
	    
	    ((EEntryFun)(env.lookUp(this.id))).setEnv0(newEnv0);
	    ((EEntryFun)(env.lookUp(this.id))).setEnv1(tmpEnv0);
        //env0.addDeclarationFun(id, env0.clone(), tmpEnv.clone());
	    
	    // SETTARE env0 ADEGUATAMENTE
        this.analyzeEffectFixPoint(env0, tmpEnv0.clone(), env); 
		return ;
	}
}