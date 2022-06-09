package ast;

public class LineCode {

	private int command;
	private String[] args = {};
	private int offset = 0;
	private String arg = "";
	
	public LineCode(int command, String[] args,int offset) {
		this.command = command;
		this.args = args;
		this.offset = offset;
	}
	
	public LineCode(int command, String[] args) {
		this.command = command;
		this.args = args;
	}
	public LineCode(int command, String arg) {
		this.command = command;
		this.arg = arg;

	}

	public int getCommand() {
		return command;
	}

	public String[] getArgs() {
		return args;
	}

	public int getOffset() {
		return offset;
	}
	public String getArg() {
		return arg;
	}
	
	
	
}
