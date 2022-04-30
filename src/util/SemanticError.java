package util;

/*
public class SemanticError {

	private final String msg;
	
	public SemanticError(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return msg;
	}
	
}
*/

public class SemanticError extends Error {

	public SemanticError(int row, int col, String msg) {
        super(row, col, msg);
    }   
    
	@Override
    public String toPrint(){
        return "Semantic error on ["+ super.row + ":" + super.col + "] : " + super.msg;
    }
	
}
