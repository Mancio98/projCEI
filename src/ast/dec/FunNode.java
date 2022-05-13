package ast.dec;

import java.util.ArrayList;

import ast.Node;
import util.Environment;
import util.Environment.DuplicateEntryException;
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
		
		this.funType = new FunType(parTypes, parATypes, this.type);
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
		//boolean retStmt = false;
		
		for(DecNode node : this.decList) {
			node.typeCheck();
		}
		
		for(Statement stmt : this.statementList) {
			Type stmtType = stmt.typeCheck();
			
			/*if (!retStmt && stmt instanceof ReturnStmt) {
				retStmt = true;
			}*/
			if (stmtType.isSubtype(this.type)) {
				if (this.statementList.indexOf(stmt) != (this.statementList.size() -1)) {
					System.out.println(new TypeError(super.row, super.column, 
							"Return is not the last statement of the function").toPrint());
					System.exit(0);
				}
			}
			
			//DA MODIFICARE
			if ((stmt instanceof ReturnStmt && !stmtType.isSubtype(this.type)) || (stmt instanceof IteStmt && !stmtType.isSubtype(this.type) && !stmtType.isSubtype(new VoidType()))) {
				System.out.println(new TypeError(super.row, super.column,
            						"[" + this.id + "] requires return type " + this.funType.getReturnType().getType() + ", instead of " + stmtType.getType()).toPrint());
				System.exit(0);
			}
			//FINO QUA
			
			if (stmt instanceof CallStmt) {
				if (!stmtType.isSubtype(new VoidType())) {
					System.out.println(new TypeError(stmt.getRow(), stmt.getColumn(),
										"Invalid call of function [" + ((CallStmt)stmt).getId() + "]").toPrint());
					System.exit(0);
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
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		
		ArrayList<SemanticError> semErrors = new  ArrayList<SemanticError>();
		
		try {
			env.addDeclaration(this.id, this.funType);
		}
		catch (DuplicateEntryException e) {
			semErrors.add(new SemanticError(super.row, super.column, "id "+ this.id + " already declared"));
		}
			
		env.entryScope();
			
		/*for(VarNode n : this.parDec.getListDec()) {
			semErrors.addAll(n.checkSemantics(env));	
		}*/
		semErrors.addAll(this.parDec.checkSemantics(env));
		
		/*for(AssetNode n : this.parAdec.getListAdec()) {
			semErrors.addAll(n.checkSemantics(env));
		}*/
		semErrors.addAll(this.parAdec.checkSemantics(env));
			
		for(DecNode n : this.decList) {
			semErrors.addAll(n.checkSemantics(env));
		}
		
		for(Statement n : this.statementList) {
			semErrors.addAll(n.checkSemantics(env));
		}
			
		env.exitScope();
		
		return semErrors;
	}
	
	
}