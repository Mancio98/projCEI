package util;

import java.util.ArrayList;
import java.util.regex.Pattern;

import ast.type.Type;
import util.EEnvironment.EffectState;

//Used for the insertion of an ID in the HashMap
public class EEntryAsset extends EEntry {
	
	//DA CAMBIARE
	private String state;
	//private ArrayList<EffectState> state;
	//private EffectState state;
	
	public EEntryAsset(int nl)
	{
		super(nl);
		this.state = "";
	} 
	   
	public EEntryAsset(int nl, String state)
	{
		super(nl);
		this.state = state;
	}
	  
	public void updateEffectState(String state)
	{
		this.state = state;
	}
	  
	public String getEffectState()
	{
		return this.state;
	}
	
	// CONTROLLARE SE VA BENE
	public static String effectStatePlus(String state1, String state2) {
		String state = state1;
		
		String[] split = state2.split(Pattern.quote("+"));
		//String[] split = (state2.contains("+") ? state2.split("+") : new String[] {state2});
		int i = 0;
		boolean exit = false;
		if (!state.equals("0") && !state.equals("1")) {
			while (!exit && i < split.length) {
				if (split[i].equals("1")) {
					state = "1";
					exit = true;
				}
				else if (!(split[i].equals("0"))) {
					if (!state.contains(split[i])) {
						state += "+" + split[i];
					}
				}
				i++;
			}
		}
		else if (state.equals("0")) {
			state = state2;
		}
		
		/*
		System.out.println("PLUS");
		System.out.println(state);
		if (state.equals("0+1")) {
			System.out.println("STATE");
			System.out.println(state1);
			System.out.println(state2);
			System.out.println("STATE");
		}
		*/
		return state;
	}
	
	// CONTROLLARE SE VA BENE
	public static String effectStateMax(String state1, String state2) {
		/*System.out.println("STATE1");
		System.out.println(state1);
		System.out.println("STATE2");
		System.out.println(state2);*/
		String state = "";
		String[] sstate = state1.split(Pattern.quote("+"));
		//String[] sstate = (state1.contains("+") ? state1.split("+") : new String[] {state1});
		
		if (sstate.length == 1 && sstate[0].equals("1")) {
			state = state1;
		}
		else if (sstate.length == 1 && sstate[0].equals("0")) {
			state = state2;
		}
		else {
			String[] smax = state2.split(Pattern.quote("+"));
			//String[] smax = (state2.contains("+") ? state2.split("+") : new String[] {state2});
			
			if (smax.length == 1 && smax[0].equals("1")) {
				state = state2;
			}
			else if (smax.length == 1 && smax[0].equals("0")) {
				state = state1;
			}
			else {
				state = state1;
				for (int i = 0; i < smax.length; i++) {
					if (!state1.contains(smax[i])) {
						state += "+" + smax[i];
					}
				}
			}
		}
		return state;
	}
	
	public String toPrint(String s) { 
		return s + "Eentry: nestlev " + Integer.toString(this.nl) + "\n" +
				s + "Eentry: state\n" + this.state + "\n";
	}
}  