package util;

public class EffectError extends Error {

	public EffectError(int row, int col, String msg) {
        super(row, col, msg);
    }   
    
	@Override
    public String toPrint() {
        return "Effect error on ["+ super.row + ":" + super.col + "] : " + super.msg;
    }
	
}
