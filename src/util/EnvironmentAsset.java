package util;

import java.util.ArrayList;
import java.util.HashMap;

import ast.type.Type;
import util.Environment.DuplicateEntryException;
import util.Environment.UndeclaredIdException;

public class EnvironmentAsset {
	
	private ArrayList<HashMap<String,STasset>>  symTable = new ArrayList<HashMap<String,STasset>>();
	private int nestingLevel = -1;
	
	//Add new HashMap when new scope is entered
	public void entryScope(){
		HashMap<String, STasset> hm = new HashMap<String,STasset>();
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
	
	public ArrayList<HashMap<String,STasset>> getSymTable(){
		return symTable;
	}
	
	//Add of a new id if it isn't already declared
	public void addDeclaration(String id) {
		//There is already an entry
		symTable.get(0).put(id, new STasset(nestingLevel, 0));
	}
	
	
	public int getState(String id) {
		
		int i = 0;
		boolean foundId = false;
		
		while(i < symTable.size() && !foundId) {
			
			
			if (symTable.get(i).get(id) != null)
				foundId = true;
			i++;
		}
		
		return symTable.get(i-1).get(id).getEffect();
		
	}
	public void update(String id, int state) {
		
		int i = 0;
		boolean foundId = false;
		
		while(i < symTable.size() && !foundId) {
			
			
			if (symTable.get(i).get(id) != null)
				foundId = true;
			i++;
		}
		
		symTable.get(i-1).get(id).setEffect(state);
		
	}
	

}
