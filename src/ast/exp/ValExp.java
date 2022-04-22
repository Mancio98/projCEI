package ast.exp;

import java.util.ArrayList;

import util.SemanticError;
import ast.IntTypeNode;
import util.Environment;

public class ValExp extends Exp {

    private final int value;

    public ValExp(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Val(" + String.valueOf(value) + ")\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }
    
    @Override
    public IntTypeNode typeCheck() {
        return new IntTypeNode();
    }

    @Override
    public String codeGeneration() {
        return "li $a0 " + value +"\n";
    }
    
}