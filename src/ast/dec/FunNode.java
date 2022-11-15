package ast.dec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import ast.IdNode;
import ast.Node;
import ast.exp.BaseExp;
import ast.exp.CallExp;
import ast.exp.Exp;
import ast.exp.NegExp;
import ast.exp.NotExp;
import ast.exp.binExp.BinExp;
import util.AssetLanlib;
import util.EEntry;
import util.EEntryAsset;
import util.EEnvironment;
import util.EffectError;
import util.EEntryFun;
import util.STEnvironment;
import util.STEnvironment.DuplicateEntryException;
import util.STEnvironment.UndeclaredIdException;
import util.STentry;
import util.SemanticError;
import util.TypeError;
import ast.statement.AssignmentStmt;
import ast.statement.CallStmt;
import ast.statement.IteStmt;
import ast.statement.PrintStmt;
import ast.statement.ReturnStmt;
import ast.statement.Statement;
import ast.type.Type;
import ast.type.AssetType;
import ast.type.VoidType;
import ast.type.FunType;

//Used to the management of function's declaration
public class FunNode extends Node {
	private Type type;
	private String id;
	private DecNode parDec;
	private AdecNode parAdec;
	private ArrayList<DecNode> decList;
	private ArrayList<Statement> statementList;
	private FunType funType;
	private STentry stentry;

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
		
		this.funType = new FunType(parTypes, parATypes, this.type,parAdec.getListAdec());
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
		
		String declbody = "";
		
		for(DecNode node : this.decList) {
			
			declbody += node.codeGeneration();
			
		}
		
		String popdeclbody = "";
		for(DecNode node : this.decList) {
			
			for(VarNode var : node.getListDec())
				popdeclbody += "pop\n";
		}
		
		String popdecl = "";
		for(VarNode var : this.parDec.getListDec()) {
			popdecl+="pop\n";
		}
		
		for(AssetNode var : this.parAdec.getListAdec()) {
			popdecl+=  "pop\n";
		}
		
		String stmcode = "";
		for(Statement stm : this.statementList) {
			stmcode += stm.codeGeneration();
		}
		
		
		String labelfun = this.stentry.getLabel();
		
		AssetLanlib.putCode(labelfun+":\n"+
		
			"move $fp $sp\n"+ 			
			"push $ra\n"+ 	
			declbody+	
			stmcode+		
			popdeclbody+
			"lw $ra $sp 0\n"+ 
			"pop\n"+
			"pop\n"+ 
			popdecl+
			"lw $fp $sp 0\n"+  
			"pop\n"+		
			"jr $ra\n"		
				
		);
		return "";
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
		
		try {
			this.stentry = env.lookUp(this.id);
		} catch (UndeclaredIdException e) {
			e.printStackTrace();
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
	
	// Analisi della liquiditï¿½
	public void analyzeLiquidity(EEnvironment env) {
		System.out.println("FUN NODE " + this.id);
		String[] split;
		ArrayList<String> liq = new ArrayList<String>();
		int i;
		
		//EEnvironment e0 = ((EEntryFun)(env.getAllFun().get(this.id))).getEnv0();
		EEnvironment env0 = ((EEntryFun)(env.getAllFun().get(this.id))).getEnv0().clone();
		System.out.println();
		System.out.println("ENV 0");
		System.out.println();
		// Aggiorno gli stati degli asset dell'ambiente di entrata della funzione in base a quelli calcolati fino ad ora
		for (String g : env.getAllAsset().keySet()) {
			((EEntryAsset)(env0.lookUp(g))).updateEffectState(((EEntryAsset)(env.lookUp(g))).getEffectState());
			System.out.println("asset: " + g);
			System.out.println("effect: " + ((EEntryAsset)(env0.lookUp(g))).getEffectState());
		}
		
		//EEnvironment e1 = ((EEntryFun)(env.getAllFun().get(this.id))).getEnv1();
		EEnvironment env1 = ((EEntryFun)(env.getAllFun().get(this.id))).getEnv1().clone();
		System.out.println();
		System.out.println();
		System.out.println("ENV 1");
		System.out.println();
		// Per ogni asset controllo se l'effetto ï¿½ formato da termini oppure no
		for (String a : env1.getAllAsset().keySet()) {
			System.out.println("asset: " + a);
			System.out.println("effect: " + ((EEntryAsset)(env1.lookUp(a))).getEffectState());
			if (!((EEntryAsset)(env1.lookUp(a))).getEffectState().equals("1") && !((EEntryAsset)(env1.lookUp(a))).getEffectState().equals("0")) {
				split = ((EEntryAsset)(env1.lookUp(a))).getEffectState().split(Pattern.quote("+"));
				
				// Nel caso sia formato da termini allora li risolvo e faccio la somma tra i valori trovati
				i = 1;
				if (!split[0].equals("0") && !split[0].equals("1")) {
					String res = ((EEntryAsset)(env0.getAllAsset().get(split[0]))).getEffectState();
					while (!res.equals("1") && !res.equals("0") && i < split.length) { 
						res = EEntryAsset.effectStatePlus(res, ((EEntryAsset)(env0.getAllAsset().get(split[i]))).getEffectState());
						i++;
					}
					
					System.out.println("-> " + res);
					((EEntryAsset)(env1.getAllAsset().get(a))).updateEffectState(res);
				}
			}	
		}
		
		// Controllo se nell'ambiente di uscita tutti i parametri formali hanno valore pari a 0, altrimenti la funzione non ï¿½ liquida
		for (AssetNode an : this.parAdec.getListAdec()) {
			if (!((EEntryAsset)(env1.lookUp(an.getId()))).getEffectState().equals("0")) {
				liq.add(an.getId());
			}
		}
		
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
			System.out.println(new EffectError(row, column, "Function [" + this.id + "] is not liquid. [" + msg + "] not empty.").toPrint());
			System.exit(0);
		}
		
		// Prendo l'ambiente di entrata della funzione che stiamo controllando
		EEnvironment env0Fun = ((EEntryFun)(env.getAllFun().get(this.id))).getEnv0();
		// Mi creo un nuovo nesting level dove aggiungo gli asset locali usati dalla funzione corrente
		env.entryScope();
		for (String add : env0Fun.getAllAsset().keySet()) {
			if (env.getAllAsset().get(add) == null) {
				env.addDeclarationAsset(add, ((EEntryAsset)(env0Fun.getAllAsset().get(add))).getEffectState());
			}	
		}
		
		System.out.println();
		System.out.println("BODY");
		// Analizzo il body della funzione
		for(Statement stmt : this.statementList) {
			stmt.analyzeLiquidity(env, this.id);
		}
		
		/*
		System.out.println("uidity");
		env.getSymTable().forEach( hashmap -> {
			System.out.println();
			hashmap.forEach( (idd, entry) -> {
				if (entry instanceof EEntryAsset) {
					System.out.println(idd);
					System.out.println(((EEntryAsset)(entry)).getEffectState());
				}
				else {
					System.out.println(idd);
				}
			});
		});
		*/
		
		env.exitScope();
		
		return ;
	}

	// Funzione ausiliaria per il controllo se la funzione ï¿½ ricorsiva
	private boolean isRecursive(Statement stmt) {
		boolean isRec = false;
		
		for(Statement s : ((IteStmt)(stmt)).getThenStmt()) {			
			if (s instanceof CallStmt && ((((CallStmt)(s)).getId()).equals(this.id))) {
				isRec = true;
			}
			else if (s instanceof AssignmentStmt) {
				isRec = isRec || expIsRecursive(((AssignmentStmt)(s)).getExp());
			}
			else if (s instanceof PrintStmt) {
				isRec = isRec || expIsRecursive(((PrintStmt)(s)).getExp());
			}
			else if (s instanceof ReturnStmt && ((ReturnStmt)(s)).getExp() != null) {
				isRec = isRec || expIsRecursive(((ReturnStmt)(s)).getExp());
			}
			else if (s instanceof IteStmt) {
				isRec = isRec || expIsRecursive(((IteStmt)(s)).getExp());
				if (!isRec) {
					isRec = isRec || isRecursive(s);
				}
			}
		}
		if (!isRec) {
			for(Statement s : ((IteStmt)(stmt)).getElseStmt()) {
				if (s instanceof CallStmt && ((((CallStmt)(s)).getId()).equals(this.id))) {
					isRec = true;
				}
				else if (s instanceof AssignmentStmt) {
					isRec = isRec || expIsRecursive(((AssignmentStmt)(s)).getExp());
				}
				else if (s instanceof PrintStmt) {
					isRec = isRec || expIsRecursive(((PrintStmt)(s)).getExp());
				}
				else if (s instanceof ReturnStmt && ((ReturnStmt)(s)).getExp() != null) {
					isRec = isRec || expIsRecursive(((ReturnStmt)(s)).getExp());
				}
				else if (s instanceof IteStmt) {
					isRec = isRec || expIsRecursive(((IteStmt)(s)).getExp());
					if (!isRec) {
						isRec = isRec || isRecursive(s);
					}
				}
			}
		}
		return isRec;
	}
	
	// Funzione ausiliaria per il controllo se la funzione ï¿½ ricorsiva
	private boolean expIsRecursive(Exp exp) {
		boolean isRec = false;
		
		if (exp instanceof CallExp && ((CallExp)(exp)).getId().equals(this.id)) {
			isRec = true;
		}
		else if (exp instanceof BinExp) {
			Exp left = ((BinExp)(exp)).getLeft();
			Exp right = ((BinExp)(exp)).getRight();
			
			if ((left instanceof CallExp && ((((CallExp)(left)).getId()).equals(this.id))) || (right instanceof CallExp && ((((CallExp)(right)).getId()).equals(this.id)))) {
				isRec = true;
			}
			else {
				isRec = isRec || expIsRecursive(left) || expIsRecursive(right);
			}
		}
		else if (exp instanceof BaseExp) {
			Exp child = ((BaseExp)(exp)).getChild();
			if (child instanceof CallExp && ((((CallExp)(child)).getId()).equals(this.id))) {
				isRec = true;
			}
			else {
				isRec = isRec || expIsRecursive(child);
			}
		}
		else if (exp instanceof NegExp) {
			Exp child = ((NegExp)(exp)).getChild();
			if (child instanceof CallExp && ((((CallExp)(child)).getId()).equals(this.id))) {
				isRec = true;
			}
			else {
				isRec = isRec || expIsRecursive(child);
			}
		}
		else if (exp instanceof NotExp) {
			Exp child = ((NotExp)(exp)).getChild();
			if (child instanceof CallExp && ((((CallExp)(child)).getId()).equals(this.id))) {
				isRec = true;
			}
			else {
				isRec = isRec || expIsRecursive(child);
			}
		}
		
		return isRec;
	}
	
	@Override
	public void analyzeEffect(EEnvironment env) {
		System.out.println("FUN NODE " + this.id);
		boolean isRec = false;
		
		// Controllo se la funzione ï¿½ ricorsiva
		for (Statement stmt : this.statementList) {		
			if (stmt instanceof CallStmt && ((((CallStmt)(stmt)).getId()).equals(this.id))) {
				isRec = true;
			}
			else if (stmt instanceof AssignmentStmt) {
				isRec = isRec || expIsRecursive(((AssignmentStmt)(stmt)).getExp());
			}
			else if (stmt instanceof PrintStmt) {
				isRec = isRec || expIsRecursive(((PrintStmt)(stmt)).getExp());
			}
			else if (stmt instanceof ReturnStmt && ((ReturnStmt)(stmt)).getExp() != null) {
				isRec = isRec || expIsRecursive(((ReturnStmt)(stmt)).getExp());
			}
			else if (stmt instanceof IteStmt) {
				isRec = isRec || expIsRecursive(((IteStmt)(stmt)).getExp());
				if (!isRec) {
					isRec = isRec || isRecursive(stmt);
				}
			}
		}
		
		System.out.println("IS RECURSIVE? " + isRec);
		env.entryScope();
		
		// Faccio l'analisi degli effetti della lista dei parametri formali e delle dichiarazioni nel body
		this.parDec.analyzeEffect(env);
		this.parAdec.analyzeEffect(env);
		for (AssetNode asset : this.parAdec.getListAdec()) {
			((EEntryAsset)(env.lookUp(asset.getId()))).updateEffectState(asset.getId());
		}
		
		// Faccio l'analisi degli effetti delle dichiarazioni nel body
		for(DecNode n : this.decList) {
			n.analyzeEffect(env);
		}
		
		// Creazione ed inizializzazione degli ambienti di entrata e uscita della funzione che stiamo valutando
		EEnvironment envFun = new EEnvironment();
		EEnvironment env1 = new EEnvironment();
		
		envFun.entryScope();
		env1.entryScope();
		
		// Creo l'ambiente delle funzioni
		HashMap<String, EEntry> mapFun = env.getAllFun();
		HashMap<String, EEntry> map10 = env.getAllFun();
		
		mapFun.forEach((id, entry) -> { envFun.addDeclaration(id, entry); });
		map10.forEach((id, entry) -> { env1.addDeclaration(id, entry); });
		
		// Se non ci sono funzioni già valutate, non aggiungiamo l'ambiente delle funzioni in testa
		if (!mapFun.isEmpty()) {
			envFun.entryScope();
			env1.entryScope();
		}
		
		// Creo l'ambiente con tutti gli asset che sono visibili all'interno della funzione
		HashMap<String, EEntry> mapAsset = env.getAllAsset();
		HashMap<String, EEntry> mapAsset1 = env.getAllAsset();
		
		mapAsset.forEach((id, entry) -> { envFun.addDeclarationAsset(id, id); });
		mapAsset1.forEach((id, entry) -> { env1.addDeclarationAsset(id, id); });
		
		// Se la funzione ï¿½ ricorsiva allora devo utilizzare il punto fisso
		if (!isRec) {		
			System.out.println("FUN BODY");
			// Se non lo ï¿½, analizzo subito il body della funzione senza fare punto fisso
			for(Statement stmt : this.statementList) {
				// Richiamo l'analisi degli effetti per ogni statement
				stmt.analyzeEffect(env1);	//QUI NON C'ï¿½ AMBIENTE COMPLETO
			}
			
			env.exitScope();
			// Dichiaro la funzione all'interno dell'ambiente
			env.addDeclarationFun(id, envFun, env1, this);
		}
		else {
			// Inizializzazione del punto fisso al caso base, ovvero con tutti gli effeti pari a 1
			for (String asset : envFun.getAllAsset().keySet()) {
				((EEntryAsset)(envFun.getAllAsset().get(asset))).updateEffectState("1");
				((EEntryAsset)(env1.getAllAsset().get(asset))).updateEffectState("1");
			}
			
			env.exitScope();
			// Dichiaro la funzione all'interno dell'ambiente, anche se non ï¿½ quella definitiva e potrebbe esser cambiata
			env.addDeclarationFun(id, envFun, env1, this);
			// Inizio la computazione del punto fisso
			analyzeEffectFixPoint(envFun, env1.clone(), env);
		}
		
		System.out.println("ENV 1");
		((EEntryFun)(env.lookUp(this.id))).getEnv1().getSymTable().forEach(  hashmap -> {
			System.out.println();
			hashmap.forEach( (id, entry) -> {
				if (entry instanceof EEntryAsset) {
					System.out.println(id);
					System.out.println(((EEntryAsset)(entry)).getEffectState());
				}
			});
		});
        System.out.println("ENV 1");
		
		return ;
	}
	
	// Analisi del punto fisso
	private void analyzeEffectFixPoint(EEnvironment env0, EEnvironment env1, EEnvironment env) {
		EEnvironment newEnv1 = ((EEntryFun)(env.lookUp(this.id))).getEnv0().clone();
		
		//EEnvironment tmpEnv = env.clone();
		//EEnvironment tmpEnv0 = ((EEntryFun)(tmpEnv.lookUp(this.id))).getEnv0();
		
		System.out.println("");
		System.out.println("FUN BODY");
		// Per ogni statement del body della funzione, faccio l'analisi
		for(Statement stmt : this.statementList) {
			stmt.analyzeEffectFixPoint(newEnv1, env, this.id);
		}
		
		// Confronto tra gli ambienti di uscita, se sono uguali il punto fisso termina
		if (EEnvironment.environmentEquality(env1, newEnv1)) {
	    	// Setto l'ambiente di entrata al caso base
			for (String asset : ((EEntryFun)(env.lookUp(this.id))).getEnv0().getAllAsset().keySet()) {
				((EEntryAsset)(((EEntryFun)(env.lookUp(this.id))).getEnv0().getAllAsset().get(asset))).updateEffectState(asset);
			}
			
	        return ;
	    }		
		
	    // Aggiorno il nuovo ambiente di uscita della funzione con quello calcolato al passo corrente del punto fisso
	    ((EEntryFun)(env.lookUp(this.id))).setEnv1(newEnv1);
	    
	    // Altro giro del punto fisso con i valori aggiornati
        this.analyzeEffectFixPoint(env0, newEnv1.clone(), env);
        
		return ;
	}
}