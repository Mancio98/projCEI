package ast.dec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import ast.IdNode;
import ast.Node;
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
			popdecl+="pop\n";
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
	
	public void analyzeLiquidity(EEnvironment env) {
		String[] split;
		int i;
		
		EEnvironment e0 = ((EEntryFun)(env.getAllFun().get(this.id))).getEnv0();
		EEnvironment env0 = e0.clone();
		for (String g : env.getAllAsset().keySet()) {
			((EEntryAsset)(env0.lookUp(g))).updateEffectState(((EEntryAsset)(env.lookUp(g))).getEffectState());
		}
		
		EEnvironment e1 = ((EEntryFun)(env.getAllFun().get(this.id))).getEnv1();
		EEnvironment env1 = e1.clone();
		
		for (String a : env1.getAllAsset().keySet()) {
			if (!((EEntryAsset)(env1.lookUp(a))).getEffectState().equals("1") && !((EEntryAsset)(env1.lookUp(a))).getEffectState().equals("0")) {
				split = ((EEntryAsset)(env1.lookUp(a))).getEffectState().split(Pattern.quote("+"));
				
				i = 1;
				if (!split[0].equals("0") && !split[0].equals("1")) {
					String res = ((EEntryAsset)(env0.getAllAsset().get(split[0]))).getEffectState();
					while (!res.equals("1") && !res.equals("0") && i < split.length) { 
						EEntryAsset.effectStatePlus(res, ((EEntryAsset)(env0.getAllAsset().get(split[i]))).getEffectState());
						i++;
					}
					
					((EEntryAsset)(env1.getAllAsset().get(a))).updateEffectState(res);
				}
			}
			
		}
	
		for (AssetNode an : this.parAdec.getListAdec()) {
			if (!((EEntryAsset)(env1.lookUp(an.getId()))).getEffectState().equals("0")) {
				System.out.println(new EffectError(row, column, "Function [" + this.id + "] is not liquid").toPrint());
				System.exit(0);
			}
		}
		
		for(Statement stmt : this.statementList) {
			
			if (stmt instanceof CallStmt) {
				if (!((CallStmt)(stmt)).getId().equals(this.id)) {
					for (String g : env.getAllAsset().keySet()) {
						((EEntryAsset)(env.lookUp(g))).updateEffectState(((EEntryAsset)(env0.lookUp(g))).getEffectState());
					}
					
					env.entryScope();
					for (String add : env0.getAllAsset().keySet()) {
						if (env.getAllAsset().get(add) == null) {
							env.addDeclarationAsset(add, ((EEntryAsset)(env0.getAllAsset().get(add))).getEffectState());
						}	
					}
					
					((CallStmt)(stmt)).analyzeLiquidity(env);
					
				}
				
			}
			else if (stmt instanceof IteStmt) {
				((IteStmt)(stmt)).analyzeLiquidity(env0, env, this.id);
			}
			else {
				stmt.analyzeLiquidity(env0);
			}
		}
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
			for(Statement stmt : this.statementList) {
				stmt.analyzeEffect(env1);
			}
			
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
		return ;
	}
	
	private void analyzeEffectFixPoint(EEnvironment env0, EEnvironment env1, EEnvironment env) {
		EEnvironment newEnv0 = new EEnvironment();
		newEnv0.entryScope();
		EEnvironment tmpEnv = env.clone();
		EEnvironment tmpEnv0 = ((EEntryFun)(tmpEnv.lookUp(this.id))).getEnv0();
		for(Statement stmt : this.statementList) {
			
			if (stmt instanceof CallStmt && ((CallStmt)(stmt)).getId().equals(this.id)) {
				ArrayList<AssetNode> aList = this.parAdec.getListAdec();
				ArrayList<IdNode> list = ((CallStmt)(stmt)).getIdList();
				
				for (int pos = list.size() - 1; pos >= 0; pos--) {
					newEnv0.addDeclarationAsset(aList.get(pos).getId(), ((EEntryAsset)(tmpEnv0.getAllAsset().get(list.get(pos).getId()))).getEffectState());
					((EEntryAsset)(tmpEnv0.getAllAsset().get(list.get(pos).getId()))).updateEffectState("0");
					
				}
				for (String a0 : tmpEnv0.getAllAsset().keySet()) {
					if (newEnv0.lookUp(a0) == null) {
						newEnv0.addDeclarationAsset(a0, ((EEntryAsset)(tmpEnv0.getAllAsset().get(a0))).getEffectState());
						}
					
				}
				
				HashMap<String, EEntry> map = env.getAllAsset();
				for (String id : map.keySet()) {
					((EEntryAsset)(map.get(id))).updateEffectState(((EEntryAsset)(env1.getAllAsset().get(id))).getEffectState());
				}
				
				for (int pos = list.size() - 1; pos >= 0; pos--) {
					((EEntryAsset)(tmpEnv0.getAllAsset().get(list.get(pos).getId()))).updateEffectState( ((EEntryAsset)( env1.lookUp( ((EEntryFun)(env.lookUp(this.id))).getFunNode().getParAdec().getListAdec().get(pos).getId() ))).getEffectState() );
				}

			}
			else {
				if (stmt instanceof IteStmt) {
					((IteStmt)(stmt)).analyzeEffectFixPoint(tmpEnv0, env, this.id);
				}
				else {
					stmt.analyzeEffect(tmpEnv0);
				}

			}
		}
		
	    if (EEnvironment.environmentEquality(env1, tmpEnv0)) {
	    
	        return ;
	    }
	    
	    ((EEntryFun)(env.lookUp(this.id))).setEnv0(newEnv0);
	    ((EEntryFun)(env.lookUp(this.id))).setEnv1(tmpEnv0);
	    
        this.analyzeEffectFixPoint(env0, tmpEnv0.clone(), env); 
		return ;
	}
	
	
	
}