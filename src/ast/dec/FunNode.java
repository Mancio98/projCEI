package ast.dec;

import java.util.ArrayList;
import java.util.HashMap;

import ast.Node;
import util.AssetLanlib;
import util.EEntry;
import util.EEntryAsset;
import util.EEnvironment;
import util.STEnvironment;
import util.STEnvironment.DuplicateEntryException;
import util.STEnvironment.UndeclaredIdException;
import util.STentry;
import util.SemanticError;
import util.TypeError;
import ast.statement.CallStmt;
import ast.statement.IteStmt;
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
			popdecl+="pop\n";
		}
		
		String stmcode = "";
		for(Statement stm : this.statementList) {
			stmcode += stm.codeGeneration();
		}
		
		
		String labelfun = this.stentry.getLabel();
		
		AssetLanlib.putCode(labelfun+":\n"+
		
			"move $fp $sp\n"+ 		// setta $fp a $sp				
			"push $ra\n"+ 		// inserimento return address
			declbody+		// inserimento dichiarazioni locali
			stmcode+		// cgen body
			popdeclbody+
			"lw $ra $sp 0\n"+ 	//lw ra top\n"	 store return address
			"pop\n"+		//pop di ra
			"pop\n"+ 		// pop di al
			popdecl+
			"lw $fp $sp 0\n"+  //lw fp top
			"pop\n"+		//pop old fp
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

	private boolean isRecursive(Statement stmt) {
		boolean isRec = false;
		
		for(Statement s : ((IteStmt)(stmt)).getThenStmt()) {
			if (stmt instanceof CallStmt && ((((CallStmt)(stmt)).getId()).equals(this.id))) {
				isRec = true;
			}
			else if (s instanceof IteStmt) {
				isRec = isRec || isRecursive(s);
			}
		}
		if (!isRec) {
			for(Statement s : ((IteStmt)(stmt)).getElseStmt()) {
				if (stmt instanceof CallStmt && ((((CallStmt)(stmt)).getId()).equals(this.id))) {
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
	public void analyzeEffect(EEnvironment env) {	
		boolean isRec = false;
		for (Statement stmt : this.statementList) {		
			if (stmt instanceof CallStmt && ((((CallStmt)(stmt)).getId()).equals(this.id))) {
				isRec = true;
			}
			else if (stmt instanceof IteStmt) {
				isRec = isRec || isRecursive(stmt);
			}
		}
		
		env.entryScope();
		
		this.parDec.analyzeEffect(env);
		this.parAdec.analyzeEffect(env);
		for (AssetNode asset : this.parAdec.getListAdec()) {
			((EEntryAsset)(env.lookUp(asset.getId()))).updateEffectState(asset.getId());
		}
		
		for(DecNode n : this.decList) {
			n.analyzeEffect(env);
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
		
		/*
		HashMap<String, EEntry> map0 = envFun.getAllAsset();
		for (String id : map0.keySet()) {
			if (env.lookUp(id) != null) {
				((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map0.get(id))).getEffectState());
			}
		}
		*/
		if (!isRec) {
			for(Statement stmt : this.statementList) {
				stmt.analyzeEffect(env1);
			}
			
			/*System.out.println("ENV0");
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
			System.out.println("ENV1");*/

			
			/*
			HashMap<String, EEntry> map1 = envFun.getAllAsset();
			for (String id : map1.keySet()) {
				if (env.lookUp(id) != null) {
					((EEntryAsset)(env.lookUp(id))).updateEffectState(((EEntryAsset)(map1.get(id))).getEffectState());
				}
			}
			*/
			env.exitScope();
			env.addDeclarationFun(id, envFun, env1, this);
		}
		else {
			analyzeEffectFixPoint(envFun, env1, env);
		}
		
		return ;
	}
	
	private void analyzeEffectFixPoint(EEnvironment env0, EEnvironment env1, EEnvironment env) {
		EEnvironment tmpEnv = env0.clone();
		
		for(Statement stmt : this.statementList) {
			stmt.analyzeEffect(tmpEnv);
		}
	        
	    if (EEnvironment.environmentEquality(env1, tmpEnv)) {
	        env.addDeclarationFun(id, env0, env1);
	        return ;
	    }
	        
        env0.addDeclarationFun(id, env0.clone(), tmpEnv.clone());
        analyzeEffectFixPoint(env0, tmpEnv.clone(), env); 
		return ;
	}
	
	
	
}