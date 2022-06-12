package util;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Environment<T> {
	
	protected ArrayList<HashMap<String, T>> symTable;
	protected int nestingLevel;
	protected int labelCount = 0;
	protected int offset = 1;
	
	public Environment() {
		this.symTable = new ArrayList<HashMap<String, T>>();
		this.nestingLevel = -1;
		this.labelCount = 0;
		this.offset = 1;
	}
	
	//Add new HashMap when new scope is entered
	public void entryScope() {
		this.symTable.add(0, new HashMap<String, T>()); 
		this.nestingLevel++;
		this.offset = 1;
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
	
}