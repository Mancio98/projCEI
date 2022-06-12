package ast;

import java.util.ArrayList;

import java.util.HashMap;
import Interpreter.ExecuteVM;
import parser.AVMBaseVisitor;
import parser.AVMLexer;
import parser.AVMParser.AddContext;
import parser.AVMParser.AddiContext;
import parser.AVMParser.AndContext;
import parser.AVMParser.AssemblyContext;
import parser.AVMParser.BranchContext;
import parser.AVMParser.BranchLessQContext;
import parser.AVMParser.BranchQContext;
import parser.AVMParser.DivContext;
import parser.AVMParser.EqualContext;
import parser.AVMParser.GreaterContext;
import parser.AVMParser.GreaterOrEqContext;
import parser.AVMParser.HaltContext;
import parser.AVMParser.JumpALContext;
import parser.AVMParser.JumpRegContext;
import parser.AVMParser.LabelContext;
import parser.AVMParser.LessContext;
import parser.AVMParser.LessOrEqContext;
import parser.AVMParser.LoadiContext;
import parser.AVMParser.LoadWContext;
import parser.AVMParser.MoveContext;
import parser.AVMParser.MultContext;
import parser.AVMParser.NotContext;
import parser.AVMParser.NotequalContext;
import parser.AVMParser.OrContext;
import parser.AVMParser.PopContext;
import parser.AVMParser.PrintContext;
import parser.AVMParser.PushContext;
import parser.AVMParser.StoreWContext;
import parser.AVMParser.SubContext;
import parser.AVMParser.TransferContext;
import util.LineCode;

public class AVMVisitorImpl extends AVMBaseVisitor<Void> {

	
	private ArrayList<LineCode> code = new ArrayList<LineCode>(ExecuteVM.CODESIZE);

    private HashMap<String,Integer> labelAdd = new HashMap<String,Integer>();
    private HashMap<Integer,String> labelRef = new HashMap<Integer,String>();

    
	public ArrayList<LineCode> getCode() {

		return code;
	}


	@Override
	public Void visitAssembly(AssemblyContext ctx) {
		
		visitChildren(ctx);
		
        for (Integer labelInt : labelRef.keySet()) {

            String labelString = labelRef.get(labelInt);
            LineCode line = code.get(labelInt);
            
            if (line.getCommand() == AVMLexer.BRANCHEQ || line.getCommand() == AVMLexer.BRANCHLESSEQ) {
            	
            	String[] args = line.getArgs();
            	
                code.set(labelInt, new LineCode(line.getCommand(), new String[] {args[0], args[1],labelAdd.get(labelString).toString()}));
                
                
            } else if (line.getCommand() == AVMLexer.BRANCH || line.getCommand() == AVMLexer.JUMPALABEL) {
                code.set(labelInt,
                        new LineCode(line.getCommand(), labelAdd.get(labelString).toString()));
            }
        }
        
		return null;
	}
	
	
	@Override
	public Void visitAdd(AddContext ctx) {
		
		code.add(new LineCode(AVMLexer.ADD, new String[] {ctx.input1.getText(), ctx.input2.getText(), ctx.output.getText()}));
		return super.visitAdd(ctx);
	}

	@Override
	public Void visitOr(OrContext ctx) {
		
		code.add(new LineCode(AVMLexer.OR, new String[] {ctx.input1.getText(), ctx.input2.getText(), ctx.output.getText()}));
		return null;
	}

	@Override
	public Void visitHalt(HaltContext ctx) {
		
		code.add(new LineCode(AVMLexer.HALT, ""));
		return null;
	}

	@Override
	public Void visitNotequal(NotequalContext ctx) {
		
		code.add(new LineCode(AVMLexer.NOTEQUAL, new String[] {ctx.input1.getText(), ctx.input2.getText(), ctx.output.getText()}));
		return null;
	}

	@Override
	public Void visitLabel(LabelContext ctx) {
		
		
		labelAdd.put(ctx.LABEL().getText(), code.size());
		return null;
	}

	@Override
	public Void visitStoreW(StoreWContext ctx) {
		

		code.add(new LineCode(AVMLexer.STOREW, new String[]{ctx.input1.getText(),ctx.input2.getText()},Integer.parseInt(ctx.offset.getText())));
		return null;
	}

	@Override
	public Void visitBranchLessQ(BranchLessQContext ctx) {
		
		labelRef.put(code.size(), ctx.label.getText());
		code.add(new LineCode(AVMLexer.BRANCHLESSEQ,new String[]{ctx.input1.getText(),ctx.input2.getText()}));
		return null;
	}

	@Override
	public Void visitPrint(PrintContext ctx) {
		
		code.add(new LineCode(AVMLexer.PRINT,new String[]{ctx.input1.getText(),ctx.input2.getText()}));
		return null;
	}

	@Override
	public Void visitJumpAL(JumpALContext ctx) {
		
		labelRef.put(code.size(), ctx.label.getText());
		code.add(new LineCode(AVMLexer.JUMPALABEL, ctx.label.getText()));
		return null;
	}
	
	@Override
	public Void visitJumpReg(JumpRegContext ctx) {
		code.add(new LineCode(AVMLexer.JUMPREG, ctx.input1.getText()));
		return null;
	}

	@Override
	public Void visitMove(MoveContext ctx) {
		
		code.add(new LineCode(AVMLexer.MOVE, new String[] {ctx.input1.getText(),ctx.input2.getText()}));
		return null;
	}

	@Override
	public Void visitEqual(EqualContext ctx) {
		code.add(new LineCode(AVMLexer.EQUAL, new String[] {ctx.input1.getText(), ctx.input2.getText(),ctx.output.getText()}));
		return null;
	}

	@Override
	public Void visitBranch(BranchContext ctx) {
		
		labelRef.put(code.size(), ctx.label.getText());
		code.add(new LineCode(AVMLexer.BRANCH, ctx.label.getText()));
		return null;
	}

	@Override
	public Void visitAddi(AddiContext ctx) {
		
		code.add(new LineCode(AVMLexer.ADDI, new String[]{ctx.input1.getText(), ctx.input2.getText(), ctx.input3.getText()}));
		return null;
	}


	@Override
	public Void visitPush(PushContext ctx) {
		
		code.add(new LineCode(AVMLexer.PUSH, ctx.input1.getText()));
		return null;
	}

	@Override
	public Void visitLess(LessContext ctx) {
		
		code.add(new LineCode(AVMLexer.LESS, new String[] {ctx.input1.getText(), ctx.input2.getText(), ctx.output.getText()}));
		return null;
	}

	@Override
	public Void visitSub(SubContext ctx) {
		
		code.add(new LineCode(AVMLexer.SUB, new String[] {ctx.input1.getText(), ctx.input2.getText(), ctx.output.getText()}));
		return super.visitSub(ctx);
	}

	@Override
	public Void visitLoadi(LoadiContext ctx) {
		
		code.add(new LineCode(AVMLexer.LOADI, new String[] {ctx.input1.getText(),ctx.input2.getText()}));
		return super.visitLoadi(ctx);
	}

	@Override
	public Void visitGreaterOrEq(GreaterOrEqContext ctx) {
		code.add(new LineCode(AVMLexer.GREATEROREQUAL, new String[] {ctx.input1.getText(), ctx.input2.getText(), ctx.output.getText()}));
		return super.visitGreaterOrEq(ctx);
	}

	@Override
	public Void visitBranchQ(BranchQContext ctx) {
		
		labelRef.put(code.size(), ctx.label.getText());
		code.add(new LineCode(AVMLexer.BRANCHEQ,new String[] {ctx.input1.getText(), ctx.input2.getText(), ctx.label.getText()}));
		return super.visitBranchQ(ctx);
	}

	@Override
	public Void visitLessOrEq(LessOrEqContext ctx) {
		code.add(new LineCode(AVMLexer.LESSOREQUAL, new String[] {ctx.input1.getText(), ctx.input2.getText(), ctx.output.getText()}));
		return super.visitLessOrEq(ctx);
	}

	@Override
	public Void visitPop(PopContext ctx) {
		
		code.add(new LineCode(AVMLexer.POP,""));
		return null;
	}

	@Override
	public Void visitDiv(DivContext ctx) {
		
		code.add(new LineCode(AVMLexer.DIV, new String[] {ctx.input1.getText(), ctx.input2.getText(), ctx.output.getText()}));
		return super.visitDiv(ctx);
	}

	@Override
	public Void visitNot(NotContext ctx) {
		code.add(new LineCode(AVMLexer.NOT, new String[] {ctx.input1.getText(), ctx.input2.getText()}));
		return super.visitNot(ctx);
	}

	@Override
	public Void visitLoadW(LoadWContext ctx) {
		
		code.add(new LineCode(AVMLexer.LOADW, new String[]{ctx.input1.getText(),ctx.input2.getText()}, Integer.parseInt(ctx.offset.getText())));
		return super.visitLoadW(ctx);
	}

	@Override
	public Void visitMult(MultContext ctx) {
		
		code.add(new LineCode(AVMLexer.MULT, new String[] {ctx.input1.getText(), ctx.input2.getText(), ctx.output.getText()}));
		return super.visitMult(ctx);
	}

	@Override
	public Void visitAnd(AndContext ctx) {
		
		code.add(new LineCode(AVMLexer.AND, new String[] {ctx.input1.getText(), ctx.input2.getText(), ctx.output.getText()}));
		return super.visitAnd(ctx);
	}

	@Override
	public Void visitTransfer(TransferContext ctx) {
		
		code.add(new LineCode(AVMLexer.TRANSFER, ctx.input1.getText()));
		return super.visitTransfer(ctx);
	}

	@Override
	public Void visitGreater(GreaterContext ctx) {
		
		code.add(new LineCode(AVMLexer.GREATER, new String[] {ctx.input1.getText(), ctx.input2.getText(), ctx.output.getText()}));
		return super.visitGreater(ctx);
	}
	
	

}
