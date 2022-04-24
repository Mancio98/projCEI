package ast;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import ast.statement.*;
import ast.exp.*;
import ast.exp.binaryExp.*;
import parser.*;
import parser.AssetLanParser.AdecContext;
import parser.AssetLanParser.AssetContext;
import parser.AssetLanParser.AssignmentContext;
import parser.AssetLanParser.BaseExpContext;
import parser.AssetLanParser.BinExpContext;
import parser.AssetLanParser.BoolExpContext;
import parser.AssetLanParser.CallContext;
import parser.AssetLanParser.CallExpContext;
import parser.AssetLanParser.DecContext;
import parser.AssetLanParser.IdExpContext;
import parser.AssetLanParser.ExpContext;
import parser.AssetLanParser.FieldContext;
import parser.AssetLanParser.FunctionContext;
import parser.AssetLanParser.InitcallContext;
import parser.AssetLanParser.IteContext;
import parser.AssetLanParser.MoveContext;
import parser.AssetLanParser.NegExpContext;
import parser.AssetLanParser.NotExpContext;
import parser.AssetLanParser.PrintContext;
import parser.AssetLanParser.ProgramContext;
import parser.AssetLanParser.RetContext;
import parser.AssetLanParser.StatementContext;
import parser.AssetLanParser.TransferContext;
import parser.AssetLanParser.TypeContext;
import parser.AssetLanParser.ValExpContext;


public class AssetLanVisitorImpl extends AssetLanBaseVisitor<Node>{
	
	@Override
	public Node visitProgram(ProgramContext ctx) {
		
		ProgramNode res;
		
		ArrayList<FieldNode> field = new ArrayList<FieldNode>();
		ArrayList<AssetNode> asset = new ArrayList<AssetNode>();
		ArrayList<FunNode> function = new ArrayList<FunNode>();
		
		for(FieldContext fc : ctx.field()) {
			field.add((FieldNode) visit(fc));
		}
		for(AssetContext ac : ctx.asset()) {
			asset.add((AssetNode) visit(ac));
		}
		for(FunctionContext func : ctx.function()) {
			function.add((FunNode) visit(func));
		}
		
		InitcallNode init = (InitcallNode) visit(ctx.initcall());
		
		res = new ProgramNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),field, asset, function, init);
		
		return res;
	}
	
	public Node visitField(FieldContext ctx) {
	
		FieldNode field;
		
		Node type = visit(ctx.type());
		Node exp;
		
		if (ctx.exp()!=null) {
			exp = visit(ctx.exp());
			field = new FieldNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.ID().getText(),type,(Exp) exp);
		}
		else field = new FieldNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),ctx.ID().getText(),type);
	
		return field;
	}
	
	public Node visitAsset(AssetContext ctx) {
				
		return new AssetNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),ctx.ID().getText());
		
	}
	
	public Node visitFunction(FunctionContext ctx) {
		
		FunNode res;
		if(ctx.type()!=null) {
			res = new FunNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),ctx.ID().getText(),visit(ctx.type()));
		}
		else res = new FunNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),ctx.ID().getText(),new VoidTypeNode(ctx.start.getLine(), ctx.start.getCharPositionInLine()));
		
		List<DecContext> decls = ctx.dec();
		int decSize = decls.size();
		
		if (decSize > 0 && ctx.adec() == null) {
			
			DecContext decParams = decls.get(0);
			
			if(decParams.invokingState == 72 ){
				
				res.addPar((DecNode)visit(decls.get(0)), null);
				decls.remove(0);
			}
			else {
				res.addPar(null, null);
			}
		}
		else if (decSize == 0 && ctx.adec() != null) {
			
			res.addPar(null, (AdecNode)visit(ctx.adec()));
		}
		else if (decSize > 0 && ctx.adec() != null) {
			
			
			DecContext decParams = decls.get(0);
			
			
			if(decParams.invokingState == 72 ){
				
				res.addPar((DecNode)visit(decls.get(0)), (AdecNode)visit(ctx.adec()));
				
				decls.remove(0);
				
			}
			else {
				res.addPar(null, (AdecNode)visit(ctx.adec()));
			}
			
		}
		else
			res.addPar(null, null);
		
		
		ArrayList<DecNode> innerDec = new ArrayList<DecNode>();
		
		
		for(DecContext dc: decls) {
			innerDec.add((DecNode)visit(dc));
		}
		
		ArrayList<Statement> innerStatement = new ArrayList<Statement>();
		
		for(StatementContext sc: ctx.statement()) {
			innerStatement.add((Statement) visit(sc));
		}
		
		res.addBody(innerDec,innerStatement);
		
		return res;
	}
	
	public DecNode visitDec(DecContext ctx){
		
		ArrayList<VarNode> dec = new ArrayList<VarNode>();
		
		
		for(int i=0; i<ctx.ID().size(); i++) {
			
			dec.add(new VarNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),ctx.ID(i).getText(),visit(ctx.type(i))));
			
		}
		
		return new DecNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),dec);
	}
	
	public AdecNode visitAdec(AdecContext ctx) {
		ArrayList<AssetNode> adec = new ArrayList<AssetNode>();
		
		if(ctx != null) {
		
			for(TerminalNode tc : ctx.ID()) {
				adec.add(new AssetNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),tc.getText()));
			}
		}
		
		return new AdecNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),adec);
	}
	
	public Node visitStatement(StatementContext ctx){
		if(ctx.assignment()!=null) {
			return visit(ctx.assignment());
		}
		if(ctx.move()!=null) {
			return visit(ctx.move());
		}
		if(ctx.print()!=null) {
			return visit(ctx.print());
		}
		if(ctx.transfer()!=null) {
			return visit(ctx.transfer());
		}
		if(ctx.ret()!=null) {
			
			return visit(ctx.ret());	
		}
		if(ctx.ite()!=null) {
			return visit(ctx.ite());
		}
		if(ctx.call()!=null) {
			return visit(ctx.call());
		}
		return null;
	}
	
	public Node visitType(TypeContext ctx) {
		if(ctx.getText().equals("int")) 
			return new IntTypeNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		else if(ctx.getText().equals("bool"))
			return new BoolTypeNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		else if(ctx.getText().equals("void"))
			return new VoidTypeNode(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		
		return null;
	}
	
	public Node visitAssignment(AssignmentContext ctx) {
		return new AssignmentStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(),new IdNode(ctx.ID().getSymbol().getLine(), ctx.ID().getSymbol().getCharPositionInLine(),ctx.ID().getText()),(Exp)visit(ctx.exp()));
	}
	
	public Node visitMove(MoveContext ctx) {
		return new MoveStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(),new IdNode(ctx.ID().get(0).getSymbol().getLine(), ctx.ID().get(0).getSymbol().getCharPositionInLine(),ctx.ID().get(0).getText()), new IdNode(ctx.ID().get(1).getSymbol().getLine(), ctx.ID().get(1).getSymbol().getCharPositionInLine(),ctx.ID().get(1).getText()));
	
	}
	
	public Node visitPrint(PrintContext ctx) {
		return new PrintStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.exp()));
	}
	
	public Node visitTransfer(TransferContext ctx) {
		return new TransferStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(),new IdNode(ctx.ID().getSymbol().getLine(), ctx.ID().getSymbol().getCharPositionInLine(),ctx.ID().getText()));
	}
	
	public Node visitRet(RetContext ctx) {
		if(ctx.exp()!=null)
			return new ReturnStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.exp()));
		else return new ReturnStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine());
	}
	
	public Node visitIte(IteContext ctx) {
			if(ctx.statement().size()>1){
				
				return new IteStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.exp()),(Statement) visit(ctx.statement().get(0)),
						(Statement) visit(ctx.statement().get(1)));
			}
			else return new IteStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.exp()),(Statement)visit(ctx.statement().get(0)));
	}

	public Node visitCall(CallContext ctx) {
		
		ArrayList<IdNode> id = new ArrayList<IdNode>();
		ArrayList<Exp> exp = new ArrayList<Exp>();
		
		String idFun = ctx.ID(0).getText();
		List<TerminalNode> temp = ctx.ID();
		temp.remove(0);
		
		for(TerminalNode cc: temp) {
			id.add(new IdNode(cc.getSymbol().getLine(),cc.getSymbol().getCharPositionInLine(),cc.getText()));
		}
		
		if (ctx.exp() != null) {
			
			for(ExpContext ec: ctx.exp()) {
				exp.add((Exp) visit(ec));
			}
		}
		return new CallNode(ctx.start.getLine(),ctx.start.getCharPositionInLine(),idFun,id,exp);
	}
	
	
	public Node visitInitcall(InitcallContext ctx) {
		
		ArrayList<Exp> exp1 = new ArrayList<Exp>();
		ArrayList<Exp> exp2 = new ArrayList<Exp>();
		

		
		for(ExpContext ec: ctx.exp()) {
			
			if(ec.invokingState == 195 || ec.invokingState == 197)
				exp1.add((Exp)visit(ec));
			else
				exp2.add((Exp)visit(ec));
		}

		return new InitcallNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),ctx.ID().getText(),exp1,exp2);
	}
	
	public Node visitBaseExp(BaseExpContext ctx) {
		return new BaseExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp) visit(ctx.exp()));
	}
	
	public Node visitNegExp(NegExpContext ctx) {
		return new NegExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp) visit(ctx.exp()));
	}
	
	public Node visitNotExp(NotExpContext ctx) {
		return new NotExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp) visit(ctx.exp()));
	}
	
	public Node visitIdExp(IdExpContext ctx) {
		return new IdNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),ctx.ID().getText());
	}
	

	public Node visitBinExp(BinExpContext ctx) {

		switch(ctx.op.getText()) {

		case "*":
			return new MulExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "/":
			return new DivExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "+":
			return new SumExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "-":
			return new SubExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "<":
			return new LessExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "<=":
			return new LessOrEqualExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case ">":
			return new GreaterExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case ">=":
			return new GreaterOrEqualExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "==":
			return new EqualExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "!=":
			return new NotEqualExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "&&":
			return new AndExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "||":
			return new OrExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.left),(Exp)visit(ctx.right));
		default:
			break;
		}
		return null;

	}
	
	public Node visitCallExp(CallExpContext ctx) {
		ArrayList<IdNode> id = new ArrayList<IdNode>();
		ArrayList<Exp> exp = new ArrayList<Exp>();
		
		String idFun = ctx.call().ID(0).getText();
		List<TerminalNode> temp = ctx.call().ID();
		temp.remove(0);
		
		
		for(TerminalNode cc: temp) {
			id.add(new IdNode(cc.getSymbol().getLine(),cc.getSymbol().getCharPositionInLine(),cc.getText()));
		}
		
		for(ExpContext ec: ctx.call().exp()) {
			exp.add((Exp) visit(ec));
		}
		
		return new CallNode(ctx.start.getLine(),ctx.start.getCharPositionInLine(),idFun,id,exp);
	}
	
	public Node visitBoolExp(BoolExpContext ctx) {
		return new BoolExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),Boolean.parseBoolean(ctx.getText()));
		
	}
	
	public Node visitValExp(ValExpContext ctx) {
		return new ValExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),Integer.parseInt(ctx.NUMBER().getText()));
	
	}
}