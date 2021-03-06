package util;

import java.util.regex.Pattern;

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
		while (!exit && i < split.length) {
			if (split[i] == "1") {
				state = "1";
				exit = true;
			}
			else if (split[i] != "0") {
				if (!state.contains(split[i])) {
					state += "+" + split[i];
				}
			}
			i++;
		}
		return state;
	}
	
	// CONTROLLARE SE VA BENE
	public static String effectStateMax(String state1, String state2) {
		String state = "";
		String[] sstate = state1.split(Pattern.quote("+"));
		//String[] sstate = (state1.contains("+") ? state1.split("+") : new String[] {state1});
		
		if (sstate.length == 1 && sstate[0] == "1") {
			state = state1;
		}
		else if (sstate.length == 1 && sstate[0] == "0") {
			state = state2;
		}
		else {
			String[] smax = state2.split(Pattern.quote("+"));
			//String[] smax = (state2.contains("+") ? state2.split("+") : new String[] {state2});
			
			if (smax.length == 1 && sstate[0] == "1") {
				state = state2;
			}
			else if (smax.length == 1 && sstate[0] == "0") {
				state = state1;
			}
			else {
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