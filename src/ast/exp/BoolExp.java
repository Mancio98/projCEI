package ast.exp;

import java.util.ArrayList;

import util.SemanticError;
import ast.BoolTypeNode;
import util.Environment;

public class BoolExp extends Exp {

    private final boolean bool;

    public BoolExp(boolean bool) {
        this.bool = bool;
    }

    public boolean getBool() {
        return this.bool;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Bool(" + String.valueOf(bool) + ")\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }
    
    @Override
    public BoolTypeNode typeCheck() {
        return new BoolTypeNode();
    }

    @Override
    public String codeGeneration() {
        return "";
    }
    
}