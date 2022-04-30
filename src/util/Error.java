package util;

public abstract class Error {

	protected final int row;
	protected final int col;
	protected final String msg;
	
	public Error(int row, int col, String msg) {
		this.row = row;
		this.col = col;
		this.msg = msg;
	}
	
	public abstract String toPrint();
	
}
