/*package ast.statement;

import java.util.ArrayList;
import ast.Node;
import ast.DecNode;
import ast.AdecNode;
import ast.exp.Exp;
import ast.exp.IdExp;
import util.SemanticError;
import util.Environment;

public class CallStmt extends Statement {

	private final String id;
    private final ArrayList<DecNode> declist;
    private final ArrayList<IdExp> adeclist;

	public CallStmt(String id) {
		this.id = id;
		
	}

	@Override
    public String toPrint(String indent) {
        return "";
    }

	@Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }

	@Override
	public Node typeCheck() {
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}

}
*/