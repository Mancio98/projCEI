package util;

import java.util.regex.Pattern;

//Used for the insertion of an ID in the HashMap
public class EEntryAsset extends EEntry {

	private String state;
	
	public EEntryAsset(int nl){
		super(nl);
		this.state = "";
	} 
	   
	public EEntryAsset(int nl, String state){
		super(nl);
		this.state = state;
	}

	// Updating the state of calling asset
	public void updateEffectState(String state){
		this.state = state;
	}

	// Return the state of calling asset
	public String getEffectState(){
		return this.state;
	}

	// Abstract sum as defined in the PDF
	public static String effectStatePlus(String state1, String state2) {
		String state = state1;

		String[] split = state2.split(Pattern.quote("+"));

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
		return state;
	}

	// Return the greater between the two passed state based on the sorting defined from the abstract domain ( 0 < 1 )
	public static String effectStateMax(String state1, String state2) {
		String state = "";
		String[] sstate = state1.split(Pattern.quote("+"));

		if (sstate.length == 1 && sstate[0].equals("1")) {
			state = state1;
		}
		else if (sstate.length == 1 && sstate[0].equals("0")) {
			state = state2;
		}
		else {
			String[] smax = state2.split(Pattern.quote("+"));

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