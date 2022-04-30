package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import ast.type.Type;
import ast.type.FunType;

public class Environment {
	
	private ArrayList<HashMap<String,STentry>>  symTable = new ArrayList<HashMap<String,STentry>>();
	private int nestingLevel = -1;
	
	//Add new HashMap when new scope is entered
	public void entryScope(){
		HashMap<String, STentry> hm = new HashMap<String,STentry>();
		symTable.add(0,hm); 
		nestingLevel++;
	}
	
	//Remove current HashMap of the scope when scope is left
	public void exitScope(){
		symTable.remove(0); 
		nestingLevel--;
	}
	
	public int getNestingLevel(){
		return nestingLevel;
	}
	
	public ArrayList<HashMap<String,STentry>> getSymTable(){
		return symTable;
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
	
	/*
	public STentry lookUpFun() {
		
		Iterator<STentry> entries = null;
		STentry fun = null;
		boolean found = false;
		
		HashMap<String, STentry> retTable = this.symTable.get(1);
		
		entries = retTable.values().iterator();
		while(entries.hasNext() && !found) {
			fun = entries.next();
			if (fun.getType() instanceof FunType)
				found = true;
		}
		
		return fun;	
	}
	*/
	
	//Extensions of Exception class for duplicate declaration
	public static class DuplicateEntryException extends Exception {
	}
	
	//Extensions of Exception class for id not found in the HashMap
	public static class UndeclaredIdException extends Exception {
	}
	
}
