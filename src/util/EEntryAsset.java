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
	 
		// Aggiorna lo stato dell'asset chiamante
		public void updateEffectState(String state){
			this.state = state;
		}
		 
		// Ritorna lo stato dell'asset chiamante
		public String getEffectState(){
			return this.state;
		}
		
		// Somma i due stati passati secondo l'operazione di somma astratta definita nel PDF
		public static String effectStatePlus(String state1, String state2) {
			String state = state1;
			
			String[] split = state2.split(Pattern.quote("+"));
			//System.out.println(state);
			int i = 0;
			boolean exit = false;
			if (!state.equals("0") && !state.equals("1")) {
				while (!exit && i < split.length) {
					//System.out.println(split[i]);
					if (split[i].equals("1")) {
						state = "1";
						exit = true;
					}
					else if (!(split[i].equals("0"))) {
						if (!state.contains(split[i])) {
							//System.out.println("OK");
							state += "+" + split[i];
							//System.out.println(state);
						}
					}
					i++;
				}
			}
			else if (state.equals("0")) {
				state = state2;
			}
			//System.out.println(state);
			return state;
		}
		
		// Ritorna il pi� grande tra i due stati passati in base all'ordinamento definito dal dominio astratto (0 < 1)
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