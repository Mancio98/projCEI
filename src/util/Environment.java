package util;

import java.util.ArrayList;
import java.util.HashMap;

import ast.Node;
import ast.STentry;

public class Environment {
	
	//THESE VARIABLES SHOULDN'T BE PUBLIC
	//THIS CAN BE DONE MUCH BETTER
	
	private ArrayList<HashMap<String,STentry>>  symTable = new ArrayList<HashMap<String,STentry>>();
	private int nestingLevel = -1;
	//livello ambiente con dichiarazioni piu' esterno � 0 (prima posizione ArrayList) invece che 1 (slides)
	//il "fronte" della lista di tabelle � symTable.get(nestingLevel)
	
	public void entryScope(){
		HashMap<String, STentry> hm = new HashMap<String,STentry>();
		symTable.add(0,hm); //AGGIUNGI IN TESTA
		nestingLevel++;
	}
	
	public void exitScope(){
		symTable.remove(0); //RIMUOVI LA TESTA
		nestingLevel--;
	}
	
	public int getNestingLevel(){
		return nestingLevel;
	}
	
	public ArrayList<HashMap<String,STentry>> getSymTable(){
		return symTable;
	}
	
	// If there is no clash of names, adds id ⟼ t to st
	public void addDeclaration(String id, Node node) throws DuplicateEntryException {
		STentry value = symTable.get(0).get(id);
		// There is already an entry
		if (value != null)
			throw new DuplicateEntryException();
		symTable.get(0).put(id, new STentry(nestingLevel, node));
	}
	
	public STentry lookUp(String id) throws UndeclaredIdException {
		
		STentry fun = null;
		
		for(HashMap<String,STentry> st : symTable) {
			fun = st.get(id);
		}
		
		if (fun == null)
			throw new UndeclaredIdException();
		
		return fun;	
	}
	
	public static class DuplicateEntryException extends Exception {
		
	}
	
	public static class UndeclaredIdException extends Exception {
		
	}
	
}
