package util;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Environment<T> {
	
	protected ArrayList<HashMap<String, T>> symTable;
	protected int nestingLevel;
	protected int labelCount;
	protected int offset;
	
	public Environment() {
		this.symTable = new ArrayList<HashMap<String, T>>();
		this.nestingLevel = -1;
		this.labelCount = 0;
		this.offset = 0;
	}
	
	//Add new HashMap when new scope is entered
	public void entryScope() {
		this.symTable.add(0, new HashMap<String, T>()); 
		this.nestingLevel++;
		this.offset = 0;
	}
	
	//Remove current HashMap of the scope when scope is left
	public void exitScope() {
		this.symTable.remove(0); 
		this.nestingLevel--;
	}
	
	public int getNestingLevel(){
		return this.nestingLevel;
	}
	
	public ArrayList<HashMap<String, T>> getSymTable(){
		return this.symTable;
	}
	
	//Add of a new id if it isn't already declared
	/*public void addDeclaration(String id, Type node) throws DuplicateEntryException {
		STentry value = symTable.get(0).get(id);
		//There is already an entry
		if (value != null)
			throw new DuplicateEntryException();
		symTable.get(0).put(id, new STentry(nestingLevel, node));
	}*/
	
	//Look if an id is already declared in any HashMap of symbol table else we raise an exceptions
	/*public STentry lookUp(String id) throws UndeclaredIdException {
		
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
	}*/
	
}