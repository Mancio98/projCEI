package ast;

import java.util.ArrayList;




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
import parser.AssetLanParser.DerExpContext;
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
import util.Type;
import util.Types;

public class AssetLanVisitorImpl extends AssetLanBaseVisitor<Node>{
	
	
	@Override
	public Node visitProgram(ProgramContext ctx) {
		
		//System.out.println("");
		
		ProgramNode res;
		
		ArrayList<Node> field = new ArrayList<Node>();
		ArrayList<Node> asset = new ArrayList<Node>();
		ArrayList<Node> function = new ArrayList<Node>();
		
		for(FieldContext fc : ctx.field()) {
			field.add(visit(fc));
		}
		for(AssetContext ac : ctx.asset()) {
			asset.add(visit(ac));
		}
		for(FunctionContext func : ctx.function()) {
			function.add(visit(func));
		}
		
		Node initcall = visit(ctx.initcall());
		
		res = new ProgramNode(field, asset, function, initcall);
		
		return res;
	}
	
	public Node visitField(FieldContext ctx) {
		
		//System.out.println("sono dentro!!!\n");
		
		FieldNode field;
		
		Node type = visit(ctx.type());
		Node exp;
		
		if (ctx.exp()!=null) {
			exp = visit(ctx.exp());
			field = new FieldNode(ctx.ID().getText(),type,exp);
		}
		else field = new FieldNode(ctx.ID().getText(),type);
	
		return field;
	}
	
	public Node visitAsset(AssetContext ctx) {
		
		//System.out.println("sono dentro!!!\n");
		
		return new AssetNode(ctx.ID().getText());
		
	}
	
	public Node visitFunction(FunctionContext ctx) {
		
		//System.out.println("sono dentro!!!\n");
		
		FunNode res;
		if(ctx.type()!=null) {
			res = new FunNode(ctx.ID().getText(),visit(ctx.type()));
		}
		else res = new FunNode(ctx.ID().getText(),new VoidTypeNode());
		
		
		int decSize = ctx.dec().size();
		
		if (decSize > 0 && ctx.adec() == null) {
			
			DecContext decParams = ctx.dec().get(0);
			if(decParams.toString().equals(new String("[72 46]"))){
				res.addPar((DecNode)visit(ctx.dec().get(0)), null);
				ctx.dec().remove(0);
			}
			else if (decParams.toString().equals(new String("[82 46]"))){
				res.addPar(null, null);
			}
		}
		else if (decSize == 0 && ctx.adec() != null) {
			
			res.addPar(null, (AdecNode)visit(ctx.adec()));
		}
		else if (decSize > 0 && ctx.adec() != null) {
			
			DecContext decParams = ctx.dec().get(0);
			if(decParams.toString().equals(new String("[72 46]"))){
				res.addPar((DecNode)visit(ctx.dec().get(0)), (AdecNode)visit(ctx.adec()));
				ctx.dec().remove(0);
			}
			else if (decParams.toString().equals(new String("[82 46]"))){
				res.addPar(null, (AdecNode)visit(ctx.adec()));
			}
			
		}
		else
			res.addPar(null, null);
		
		
		ArrayList<DecNode> innerDec = new ArrayList<DecNode>();
		
		for(DecContext dc: ctx.dec()) {
			innerDec.add((DecNode)visit(dc));
		}
		
		ArrayList<Node> innerStatement = new ArrayList<Node>();
		
		for(StatementContext sc: ctx.statement()) {
			innerStatement.add(visit(sc));
		}
		
		res.addBody(innerDec,innerStatement);
		
		return res;
	}
	
	public DecNode visitDec(DecContext ctx){
		
		ArrayList<VarNode> dec = new ArrayList<VarNode>();
		
		
		System.out.println("len id:"+ctx.ID().size()+"\n");
		System.out.println("len type:"+ctx.type().size()+"\n");
		
		for(int i=0; i<ctx.ID().size(); i++) {
			
			dec.add(new VarNode(ctx.ID(i).getText(),visit(ctx.type(i))));
			
		}
		
		return new DecNode(dec);
	}
	
	public AdecNode visitAdec(AdecContext ctx) {
		ArrayList<AssetNode> adec = new ArrayList<AssetNode>();
		
		if(ctx != null) {
		
			for(TerminalNode tc : ctx.ID()) {
				adec.add(new AssetNode(tc.getText()));
			}
		}
		
		return new AdecNode(adec);
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
			return new IntTypeNode();
		else if(ctx.getText().equals("bool"))
			return new BoolTypeNode();
		else if(ctx.getText().equals("void"))
			return new VoidTypeNode();
		
		return null;
	}
	
	public Node visitAssignement(AssignmentContext ctx) {
		return new AssignmentStmt(new IdExp(ctx.ID().getText()),(Exp)visit(ctx.exp()));
	}
	
	public Node visitMove(MoveContext ctx) {
		return new MoveStmt(new IdExp(ctx.ID().get(0).getText()), new IdExp(ctx.ID().get(1).getText()));
	}
	
	public Node visitPrint(PrintContext ctx) {
		return new PrintStmt((Exp)visit(ctx.exp()));
	}
	
	public Node visitTransfer(TransferContext ctx) {
		return new TransferStmt(new IdExp(ctx.ID().getText()));
	}
	
	public Node visitRet(RetContext ctx) {
		if(ctx.exp()!=null)
			return new ReturnStmt((Exp)visit(ctx.exp()));
		else return new ReturnStmt();
	}
	
	public Node visitIte(IteContext ctx) {
			if(ctx.statement().size()>1){
				return new IteStmt((Exp)visit(ctx.exp()),(Statement)visit(ctx.statement().get(0)),(Statement)visit(ctx.statement().get(1)));
			}
			else return new IteStmt((Exp)visit(ctx.exp()),(Statement)visit(ctx.statement().get(0)));
	}

	public Node visitCall(CallContext ctx) {
		
		ArrayList<String> id = new ArrayList<String>();
		ArrayList<Node> exp = new ArrayList<Node>();
		
		
		for(TerminalNode cc: ctx.ID()) {
			id.add(cc.getText());
		}
		
		if (ctx.exp() != null) {
			
			for(ExpContext ec: ctx.exp()) {
				exp.add(visit(ec));
			}
		}
		return new CallNode(id,exp);
	}
	
	
	
	public Node visitInitCall(InitcallContext ctx) {
		ArrayList<Node> expList = new ArrayList<Node>();
		for(ExpContext ec: ctx.exp()) {
			expList.add(visit(ec));
		}
		
		return new InitcallNode(ctx.ID().getText(),expList);
	}
	
	public Node visitBaseExp(BaseExpContext ctx) {
		return new BaseExp((Exp) visit(ctx.exp()));
	}
	
	public Node visitNegExp(NegExpContext ctx) {
		return new NegExp((Exp) visit(ctx.exp()));
	}
	
	public Node visitNotExp(NotExpContext ctx) {
		return new NotExp((Exp) visit(ctx.exp()));
	}
	
	public Node visitIdExp(DerExpContext ctx) {
		return new IdExp(ctx.ID().getText());
	}
	
	
	public Node visitBinExp(BinExpContext ctx) {

		switch(ctx.op.getText()) {

		case "*":
			return new MulExp((Exp)visit(ctx.left),(Exp)visit(ctx.right));

		case "/":
			return new DivExp((Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "+":
			return new SumExp((Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "-":
			return new SubExp((Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "<":
			return new LessExp((Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "<=":
			return new LessOrEqualExp((Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case ">":
			return new GreaterExp((Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case ">=":
			return new GreaterOrEqualExp((Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "==":
			return new EqualExp((Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "!=":
			return new NotEqualExp((Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "&&":
			return new AndExp((Exp)visit(ctx.left),(Exp)visit(ctx.right));
		case "||":
			return new OrExp((Exp)visit(ctx.left),(Exp)visit(ctx.right));
			
		default:
			break;
		}
		return null;

	}
	
	public Node visitCallExp(CallExpContext ctx) {
		ArrayList<String> id = new ArrayList<String>();
		ArrayList<Node> exp = new ArrayList<Node>();
		
		
		for(TerminalNode cc: ctx.call().ID()) {
			id.add(cc.getText());
		}
		
		for(ExpContext ec: ctx.call().exp()) {
			exp.add(visit(ec));
		}
		
		return new CallNode(id,exp);
	}
	
	public Node visitBoolExp(BoolExpContext ctx) {
		return new BoolExp(Boolean.parseBoolean(ctx.getText()));
		
	}
	
	public Node visitValExp(ValExpContext ctx) {
		return new ValExp(Integer.parseInt(ctx.NUMBER().getText()));
	
	}
}