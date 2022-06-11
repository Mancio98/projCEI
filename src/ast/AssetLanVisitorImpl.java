package ast;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import ast.statement.*;
import ast.exp.*;
import ast.exp.binExp.*;
import ast.dec.*;
import ast.type.*;
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
import parser.AssetLanParser.ExpinitContext;
import parser.AssetLanParser.BinExpInitContext;
import parser.AssetLanParser.BaseExpInitContext;
import parser.AssetLanParser.ValExpInitContext;


// DA RIVEDERE QUASI TUTTO PER VEDERE POSSIBILI PROBLEMATICHE (TIPO ORDINE DEI PARAMETRI PASSATI, ETC)

public class AssetLanVisitorImpl extends AssetLanBaseVisitor<Node>{
	
	@Override
	public Node visitProgram(ProgramContext ctx) {
		
		ProgramNode res;
		System.out.println(ctx.field());
		System.out.println(ctx.asset());
		System.out.println(ctx.function());
		System.out.println(ctx.initcall());
		
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
	
	@Override
	public Node visitField(FieldContext ctx) {
		
		FieldNode field;
		
		Type type = (Type) visit(ctx.type());
		Exp exp;
		
		if (ctx.exp() != null) {
			exp = (Exp) visit(ctx.exp());
			field = new FieldNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(), type, ctx.ID().getText(), (Exp) exp);
		}
		else
			field = new FieldNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(), type, ctx.ID().getText());
	
		return field;
	}
	
	@Override
	public Node visitAsset(AssetContext ctx) {
				
		return new AssetNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),ctx.ID().getText());
		
	}
	@Override
	public Node visitFunction(FunctionContext ctx) {
			
			FunNode res;
			if(ctx.type() != null) {
				res = new FunNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Type) visit(ctx.type()), ctx.ID().getText());
			}
			else
				// CONTROLLARE LA CORRETTEZA
				res = new FunNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(), new VoidType(ctx.start.getLine(), ctx.start.getCharPositionInLine()), ctx.ID().getText());
			
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
	
	@Override
	public DecNode visitDec(DecContext ctx){
		
		ArrayList<VarNode> dec = new ArrayList<VarNode>();
		
		for(int i=0; i<ctx.ID().size(); i++) {
			dec.add(new VarNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(), (Type) visit(ctx.type(i)), ctx.ID(i).getText()));
		}
		
		return new DecNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(), dec);
	}
	
	@Override
	public AdecNode visitAdec(AdecContext ctx) {
		ArrayList<AssetNode> adec = new ArrayList<AssetNode>();
		
		if(ctx != null) {
			for(TerminalNode tc : ctx.ID()) {
				adec.add(new AssetNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),tc.getText()));
			}
		}
		
		return new AdecNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(), adec);
	}
	
	@Override
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
	
	@Override
	public Node visitType(TypeContext ctx) {
		if(ctx.getText().equals("int")) 
			return new IntType(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		else if(ctx.getText().equals("bool"))
			return new BoolType(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		else if(ctx.getText().equals("void"))
			return new VoidType(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		
		return null;
	}
	
	@Override
	public Node visitAssignment(AssignmentContext ctx) {
		return new AssignmentStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(),new IdNode(ctx.ID().getSymbol().getLine(), ctx.ID().getSymbol().getCharPositionInLine(),ctx.ID().getText()),(Exp)visit(ctx.exp()));
	}
	
	@Override
	public Node visitMove(MoveContext ctx) {
		return new MoveStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(),new IdNode(ctx.ID().get(0).getSymbol().getLine(), ctx.ID().get(0).getSymbol().getCharPositionInLine(),ctx.ID().get(0).getText()), new IdNode(ctx.ID().get(1).getSymbol().getLine(), ctx.ID().get(1).getSymbol().getCharPositionInLine(),ctx.ID().get(1).getText()));
	
	}
	
	@Override
	public Node visitPrint(PrintContext ctx) {
		return new PrintStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.exp()));
	}
	
	@Override
	public Node visitTransfer(TransferContext ctx) {
		return new TransferStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(),new IdNode(ctx.ID().getSymbol().getLine(), ctx.ID().getSymbol().getCharPositionInLine(),ctx.ID().getText()));
	}
	
	@Override
	public Node visitRet(RetContext ctx) {
		if(ctx.exp()!=null)
			return new ReturnStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp)visit(ctx.exp()));
		else return new ReturnStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine());
	}
	
	
	@Override
	public Node visitIte(IteContext ctx) {
		ArrayList<Statement> thenStatement = new ArrayList<Statement>();
		ArrayList<Statement> elseStatement = new ArrayList<Statement>();
		
		for(StatementContext sc: ctx.statement()) {
			if(sc.invokingState == 165) {
				thenStatement.add((Statement)visit(sc));
			}
			else if (sc.invokingState == 174) {
				elseStatement.add((Statement)visit(sc));
			}
			else {
				return null;
			}
		}
	
		return new IteStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(), (Exp)visit(ctx.exp()), thenStatement, elseStatement);
	}

	@Override
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
		return new CallStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(), idFun, exp, id);
	}
	
	@Override
	public Node visitBinExpInit(BinExpInitContext ctx) {
		switch(ctx.op.getText()) {
			case "*":
				return new MulExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(), (Exp)visit(ctx.left), (Exp)visit(ctx.right));
			case "/":
				return new DivExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(), (Exp)visit(ctx.left), (Exp)visit(ctx.right));
			case "+":
				return new SumExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(), (Exp)visit(ctx.left), (Exp)visit(ctx.right));
			case "-":
				return new SubExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(), (Exp)visit(ctx.left), (Exp)visit(ctx.right));
			default:
				break;
		}
		return null;
	}
	
	@Override
	public Node visitBaseExpInit(BaseExpInitContext ctx) {
		return new BaseExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(), (Exp) visit(ctx.expinit()));
	}
	
	@Override
	public Node visitValExpInit(ValExpInitContext ctx) {
		return new ValExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(), Integer.parseInt(ctx.NUMBER().getText()));
	}
	
	@Override
	public Node visitInitcall(InitcallContext ctx) {			
			System.out.println(ctx);
			System.out.println("PROVA");
			System.out.println(ctx.ID());
			ArrayList<Exp> exp1 = new ArrayList<Exp>();
			ArrayList<Exp> exp2 = new ArrayList<Exp>();
			
			for(ExpinitContext ec: ctx.expinit()) {
				if(ec.invokingState == 211 || ec.invokingState == 213) {
					exp1.add((Exp)visit(ec));
				}
				else {
					exp2.add((Exp)visit(ec));
				}
			}
			
			/*for(ExpContext ec: ctx.exp()) {
				
				if(ec.invokingState == 195 || ec.invokingState == 197)
					exp1.add((Exp)visit(ec));
				else
					exp2.add((Exp)visit(ec));
			}*/

			return new InitcallNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.ID().getText(), exp1, exp2);
	}

	@Override
	public Node visitBaseExp(BaseExpContext ctx) {
		return new BaseExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp) visit(ctx.exp()));
	}
	
	@Override
	public Node visitNegExp(NegExpContext ctx) {
		return new NegExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp) visit(ctx.exp()));
	}
	
	@Override
	public Node visitNotExp(NotExpContext ctx) {
		return new NotExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),(Exp) visit(ctx.exp()));
	}
	
	@Override
	public Node visitIdExp(IdExpContext ctx) {
		return new IdNode(ctx.start.getLine(), ctx.start.getCharPositionInLine(),ctx.ID().getText());
	}
	

	@Override
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
	
	@Override
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
		
		return new CallStmt(ctx.start.getLine(), ctx.start.getCharPositionInLine(), idFun, exp, id);
	}
	
	@Override
	public Node visitBoolExp(BoolExpContext ctx) {
		return new BoolExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(),Boolean.parseBoolean(ctx.getText()));
		
	}
	
	@Override
	public Node visitValExp(ValExpContext ctx) {
		return new ValExp(ctx.start.getLine(), ctx.start.getCharPositionInLine(), Integer.parseInt(ctx.NUMBER().getText()));
	
	}
}