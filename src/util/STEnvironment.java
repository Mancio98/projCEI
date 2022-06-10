package util;

import java.util.ArrayList;
import java.util.HashMap;
import ast.type.Type;
import ast.type.FunType;

public class STEnvironment extends Environment<STentry> {
	
	public STEnvironment() {
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
	
	public ArrayList<HashMap<String, STentry>> getSymTable(){
		return super.getSymTable();
	}
	
	//Add of a new id if it isn't already declared
	public void addDeclaration(String id, Type node) throws DuplicateEntryException {
		STentry value = symTable.get(0).get(id);
		//There is already an entry
		if (value != null)
			throw new DuplicateEntryException();
		symTable.get(0).put(id, new STentry(nestingLevel, node));
	}
	
	//Look if an id is already declared in any HashMap of symbol table else we raise an exceptions
	public STentry lookUp(String id) throws UndeclaredIdException {
		
		STentry fun = null;
		boolean foundId = false;
		
		int i = 0;
		while(i < symTable.size() && !foundId) {
			
			fun = symTable.get(i).get(id);
			if (fun != null)
				foundId = true;
			i++;
		}
		
		if (fun == null)
			throw new UndeclaredIdException();
		
		return fun;	
	}
	
	//Extensions of Exception class for duplicate declaration
	public static class DuplicateEntryException extends Exception {
	}
	
	//Extensions of Exception class for id not found in the HashMap
	public static class UndeclaredIdException extends Exception {
	}
	
}
