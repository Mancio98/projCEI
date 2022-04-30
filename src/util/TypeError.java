package util;

public class TypeError extends Error {

	public TypeError(int row, int col, String msg) {
        super(row, col, msg);
    }   
    
	@Override
    public String toPrint(){
        return "Type error on ["+ super.row + ":" + super.col + "] : " + super.msg;
    }
	
}
