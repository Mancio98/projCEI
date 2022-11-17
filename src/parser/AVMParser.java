// Generated from AVM.g4 by ANTLR 4.7.1
package parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AVMParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PUSH=1, POP=2, ADD=3, ADDI=4, MOVE=5, TRANSFER=6, NOT=7, SUB=8, MULT=9, 
		DIV=10, AND=11, OR=12, EQUAL=13, NOTEQUAL=14, GREATER=15, GREATEROREQUAL=16, 
		LESS=17, LESSOREQUAL=18, STOREW=19, LOADW=20, LOADI=21, BRANCH=22, BRANCHEQ=23, 
		BRANCHLESSEQ=24, JUMPALABEL=25, JUMPREG=26, PRINT=27, HALT=28, COL=29, 
		LABEL=30, NUMBER=31, REGISTER=32, WHITESP=33, ERR=34;
	public static final int
		RULE_assembly = 0, RULE_instruction = 1;
	public static final String[] ruleNames = {
		"assembly", "instruction"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'push'", "'pop'", "'add'", "'addi'", "'move'", "'transf'", "'not'", 
		"'sub'", "'mult'", "'div'", "'and'", "'or'", "'eq'", "'ne'", "'gre'", 
		"'goe'", "'less'", "'loe'", "'sw'", "'lw'", "'li'", "'b'", "'beq'", "'bleq'", 
		"'jal'", "'jr'", "'print'", "'halt'", "':'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "PUSH", "POP", "ADD", "ADDI", "MOVE", "TRANSFER", "NOT", "SUB", 
		"MULT", "DIV", "AND", "OR", "EQUAL", "NOTEQUAL", "GREATER", "GREATEROREQUAL", 
		"LESS", "LESSOREQUAL", "STOREW", "LOADW", "LOADI", "BRANCH", "BRANCHEQ", 
		"BRANCHLESSEQ", "JUMPALABEL", "JUMPREG", "PRINT", "HALT", "COL", "LABEL", 
		"NUMBER", "REGISTER", "WHITESP", "ERR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "AVM.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AVMParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class AssemblyContext extends ParserRuleContext {
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
		}
		public AssemblyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assembly; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitAssembly(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblyContext assembly() throws RecognitionException {
		AssemblyContext _localctx = new AssemblyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_assembly);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(7);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PUSH) | (1L << POP) | (1L << ADD) | (1L << ADDI) | (1L << MOVE) | (1L << TRANSFER) | (1L << NOT) | (1L << SUB) | (1L << MULT) | (1L << DIV) | (1L << AND) | (1L << OR) | (1L << EQUAL) | (1L << NOTEQUAL) | (1L << GREATER) | (1L << GREATEROREQUAL) | (1L << LESS) | (1L << LESSOREQUAL) | (1L << STOREW) | (1L << LOADW) | (1L << LOADI) | (1L << BRANCH) | (1L << BRANCHEQ) | (1L << BRANCHLESSEQ) | (1L << JUMPALABEL) | (1L << JUMPREG) | (1L << PRINT) | (1L << HALT) | (1L << LABEL))) != 0)) {
				{
				{
				setState(4);
				instruction();
				}
				}
				setState(9);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstructionContext extends ParserRuleContext {
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
	 
		public InstructionContext() { }
		public void copyFrom(InstructionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AddContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token output;
		public TerminalNode ADD() { return getToken(AVMParser.ADD, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public AddContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitAdd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LoadiContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public TerminalNode LOADI() { return getToken(AVMParser.LOADI, 0); }
		public TerminalNode REGISTER() { return getToken(AVMParser.REGISTER, 0); }
		public TerminalNode NUMBER() { return getToken(AVMParser.NUMBER, 0); }
		public LoadiContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitLoadi(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token output;
		public TerminalNode OR() { return getToken(AVMParser.OR, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public OrContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class HaltContext extends InstructionContext {
		public TerminalNode HALT() { return getToken(AVMParser.HALT, 0); }
		public HaltContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitHalt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotequalContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token output;
		public TerminalNode NOTEQUAL() { return getToken(AVMParser.NOTEQUAL, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public NotequalContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitNotequal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LabelContext extends InstructionContext {
		public TerminalNode LABEL() { return getToken(AVMParser.LABEL, 0); }
		public TerminalNode COL() { return getToken(AVMParser.COL, 0); }
		public LabelContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JumpRegContext extends InstructionContext {
		public Token input1;
		public TerminalNode JUMPREG() { return getToken(AVMParser.JUMPREG, 0); }
		public TerminalNode REGISTER() { return getToken(AVMParser.REGISTER, 0); }
		public JumpRegContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitJumpReg(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StoreWContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token offset;
		public TerminalNode STOREW() { return getToken(AVMParser.STOREW, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public TerminalNode NUMBER() { return getToken(AVMParser.NUMBER, 0); }
		public StoreWContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitStoreW(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchLessQContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token label;
		public TerminalNode BRANCHLESSEQ() { return getToken(AVMParser.BRANCHLESSEQ, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public TerminalNode LABEL() { return getToken(AVMParser.LABEL, 0); }
		public BranchLessQContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitBranchLessQ(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public TerminalNode PRINT() { return getToken(AVMParser.PRINT, 0); }
		public TerminalNode REGISTER() { return getToken(AVMParser.REGISTER, 0); }
		public TerminalNode NUMBER() { return getToken(AVMParser.NUMBER, 0); }
		public PrintContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitPrint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JumpALContext extends InstructionContext {
		public Token label;
		public TerminalNode JUMPALABEL() { return getToken(AVMParser.JUMPALABEL, 0); }
		public TerminalNode LABEL() { return getToken(AVMParser.LABEL, 0); }
		public JumpALContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitJumpAL(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MoveContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public TerminalNode MOVE() { return getToken(AVMParser.MOVE, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public MoveContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitMove(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token output;
		public TerminalNode EQUAL() { return getToken(AVMParser.EQUAL, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public EqualContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchContext extends InstructionContext {
		public Token label;
		public TerminalNode BRANCH() { return getToken(AVMParser.BRANCH, 0); }
		public TerminalNode LABEL() { return getToken(AVMParser.LABEL, 0); }
		public BranchContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitBranch(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddiContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token input3;
		public TerminalNode ADDI() { return getToken(AVMParser.ADDI, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public TerminalNode NUMBER() { return getToken(AVMParser.NUMBER, 0); }
		public AddiContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitAddi(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PushContext extends InstructionContext {
		public Token input1;
		public TerminalNode PUSH() { return getToken(AVMParser.PUSH, 0); }
		public TerminalNode REGISTER() { return getToken(AVMParser.REGISTER, 0); }
		public TerminalNode NUMBER() { return getToken(AVMParser.NUMBER, 0); }
		public PushContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitPush(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token output;
		public TerminalNode LESS() { return getToken(AVMParser.LESS, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public LessContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitLess(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SubContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token output;
		public TerminalNode SUB() { return getToken(AVMParser.SUB, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public SubContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitSub(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchQContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token label;
		public TerminalNode BRANCHEQ() { return getToken(AVMParser.BRANCHEQ, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public TerminalNode LABEL() { return getToken(AVMParser.LABEL, 0); }
		public BranchQContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitBranchQ(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GreaterOrEqContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token output;
		public TerminalNode GREATEROREQUAL() { return getToken(AVMParser.GREATEROREQUAL, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public GreaterOrEqContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitGreaterOrEq(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessOrEqContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token output;
		public TerminalNode LESSOREQUAL() { return getToken(AVMParser.LESSOREQUAL, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public LessOrEqContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitLessOrEq(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PopContext extends InstructionContext {
		public TerminalNode POP() { return getToken(AVMParser.POP, 0); }
		public PopContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitPop(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DivContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token output;
		public TerminalNode DIV() { return getToken(AVMParser.DIV, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public DivContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public TerminalNode NOT() { return getToken(AVMParser.NOT, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public NotContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitNot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LoadWContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token offset;
		public TerminalNode LOADW() { return getToken(AVMParser.LOADW, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public TerminalNode NUMBER() { return getToken(AVMParser.NUMBER, 0); }
		public LoadWContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitLoadW(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token output;
		public TerminalNode MULT() { return getToken(AVMParser.MULT, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public MultContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitMult(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token output;
		public TerminalNode AND() { return getToken(AVMParser.AND, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public AndContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TransferContext extends InstructionContext {
		public Token input1;
		public TerminalNode TRANSFER() { return getToken(AVMParser.TRANSFER, 0); }
		public TerminalNode REGISTER() { return getToken(AVMParser.REGISTER, 0); }
		public TransferContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitTransfer(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GreaterContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public Token output;
		public TerminalNode GREATER() { return getToken(AVMParser.GREATER, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(AVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(AVMParser.REGISTER, i);
		}
		public GreaterContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AVMVisitor ) return ((AVMVisitor<? extends T>)visitor).visitGreater(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_instruction);
		int _la;
		try {
			setState(104);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PUSH:
				_localctx = new PushContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(10);
				match(PUSH);
				setState(11);
				((PushContext)_localctx).input1 = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==NUMBER || _la==REGISTER) ) {
					((PushContext)_localctx).input1 = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case POP:
				_localctx = new PopContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(12);
				match(POP);
				}
				break;
			case ADD:
				_localctx = new AddContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(13);
				match(ADD);
				setState(14);
				((AddContext)_localctx).input1 = match(REGISTER);
				setState(15);
				((AddContext)_localctx).input2 = match(REGISTER);
				setState(16);
				((AddContext)_localctx).output = match(REGISTER);
				}
				break;
			case ADDI:
				_localctx = new AddiContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(17);
				match(ADDI);
				setState(18);
				((AddiContext)_localctx).input1 = match(REGISTER);
				setState(19);
				((AddiContext)_localctx).input2 = match(REGISTER);
				setState(20);
				((AddiContext)_localctx).input3 = match(NUMBER);
				}
				break;
			case MOVE:
				_localctx = new MoveContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(21);
				match(MOVE);
				setState(22);
				((MoveContext)_localctx).input1 = match(REGISTER);
				setState(23);
				((MoveContext)_localctx).input2 = match(REGISTER);
				}
				break;
			case NOT:
				_localctx = new NotContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(24);
				match(NOT);
				setState(25);
				((NotContext)_localctx).input1 = match(REGISTER);
				setState(26);
				((NotContext)_localctx).input2 = match(REGISTER);
				}
				break;
			case SUB:
				_localctx = new SubContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(27);
				match(SUB);
				setState(28);
				((SubContext)_localctx).input1 = match(REGISTER);
				setState(29);
				((SubContext)_localctx).input2 = match(REGISTER);
				setState(30);
				((SubContext)_localctx).output = match(REGISTER);
				}
				break;
			case MULT:
				_localctx = new MultContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(31);
				match(MULT);
				setState(32);
				((MultContext)_localctx).input1 = match(REGISTER);
				setState(33);
				((MultContext)_localctx).input2 = match(REGISTER);
				setState(34);
				((MultContext)_localctx).output = match(REGISTER);
				}
				break;
			case DIV:
				_localctx = new DivContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(35);
				match(DIV);
				setState(36);
				((DivContext)_localctx).input1 = match(REGISTER);
				setState(37);
				((DivContext)_localctx).input2 = match(REGISTER);
				setState(38);
				((DivContext)_localctx).output = match(REGISTER);
				}
				break;
			case AND:
				_localctx = new AndContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(39);
				match(AND);
				setState(40);
				((AndContext)_localctx).input1 = match(REGISTER);
				setState(41);
				((AndContext)_localctx).input2 = match(REGISTER);
				setState(42);
				((AndContext)_localctx).output = match(REGISTER);
				}
				break;
			case OR:
				_localctx = new OrContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(43);
				match(OR);
				setState(44);
				((OrContext)_localctx).input1 = match(REGISTER);
				setState(45);
				((OrContext)_localctx).input2 = match(REGISTER);
				setState(46);
				((OrContext)_localctx).output = match(REGISTER);
				}
				break;
			case TRANSFER:
				_localctx = new TransferContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(47);
				match(TRANSFER);
				setState(48);
				((TransferContext)_localctx).input1 = match(REGISTER);
				}
				break;
			case EQUAL:
				_localctx = new EqualContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(49);
				match(EQUAL);
				setState(50);
				((EqualContext)_localctx).input1 = match(REGISTER);
				setState(51);
				((EqualContext)_localctx).input2 = match(REGISTER);
				setState(52);
				((EqualContext)_localctx).output = match(REGISTER);
				}
				break;
			case NOTEQUAL:
				_localctx = new NotequalContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(53);
				match(NOTEQUAL);
				setState(54);
				((NotequalContext)_localctx).input1 = match(REGISTER);
				setState(55);
				((NotequalContext)_localctx).input2 = match(REGISTER);
				setState(56);
				((NotequalContext)_localctx).output = match(REGISTER);
				}
				break;
			case GREATER:
				_localctx = new GreaterContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(57);
				match(GREATER);
				setState(58);
				((GreaterContext)_localctx).input1 = match(REGISTER);
				setState(59);
				((GreaterContext)_localctx).input2 = match(REGISTER);
				setState(60);
				((GreaterContext)_localctx).output = match(REGISTER);
				}
				break;
			case GREATEROREQUAL:
				_localctx = new GreaterOrEqContext(_localctx);
				enterOuterAlt(_localctx, 16);
				{
				setState(61);
				match(GREATEROREQUAL);
				setState(62);
				((GreaterOrEqContext)_localctx).input1 = match(REGISTER);
				setState(63);
				((GreaterOrEqContext)_localctx).input2 = match(REGISTER);
				setState(64);
				((GreaterOrEqContext)_localctx).output = match(REGISTER);
				}
				break;
			case LESS:
				_localctx = new LessContext(_localctx);
				enterOuterAlt(_localctx, 17);
				{
				setState(65);
				match(LESS);
				setState(66);
				((LessContext)_localctx).input1 = match(REGISTER);
				setState(67);
				((LessContext)_localctx).input2 = match(REGISTER);
				setState(68);
				((LessContext)_localctx).output = match(REGISTER);
				}
				break;
			case LESSOREQUAL:
				_localctx = new LessOrEqContext(_localctx);
				enterOuterAlt(_localctx, 18);
				{
				setState(69);
				match(LESSOREQUAL);
				setState(70);
				((LessOrEqContext)_localctx).input1 = match(REGISTER);
				setState(71);
				((LessOrEqContext)_localctx).input2 = match(REGISTER);
				setState(72);
				((LessOrEqContext)_localctx).output = match(REGISTER);
				}
				break;
			case STOREW:
				_localctx = new StoreWContext(_localctx);
				enterOuterAlt(_localctx, 19);
				{
				setState(73);
				match(STOREW);
				setState(74);
				((StoreWContext)_localctx).input1 = match(REGISTER);
				setState(75);
				((StoreWContext)_localctx).input2 = match(REGISTER);
				setState(76);
				((StoreWContext)_localctx).offset = match(NUMBER);
				}
				break;
			case LOADW:
				_localctx = new LoadWContext(_localctx);
				enterOuterAlt(_localctx, 20);
				{
				setState(77);
				match(LOADW);
				setState(78);
				((LoadWContext)_localctx).input1 = match(REGISTER);
				setState(79);
				((LoadWContext)_localctx).input2 = match(REGISTER);
				setState(80);
				((LoadWContext)_localctx).offset = match(NUMBER);
				}
				break;
			case LOADI:
				_localctx = new LoadiContext(_localctx);
				enterOuterAlt(_localctx, 21);
				{
				setState(81);
				match(LOADI);
				setState(82);
				((LoadiContext)_localctx).input1 = match(REGISTER);
				setState(83);
				((LoadiContext)_localctx).input2 = match(NUMBER);
				}
				break;
			case BRANCH:
				_localctx = new BranchContext(_localctx);
				enterOuterAlt(_localctx, 22);
				{
				setState(84);
				match(BRANCH);
				setState(85);
				((BranchContext)_localctx).label = match(LABEL);
				}
				break;
			case BRANCHEQ:
				_localctx = new BranchQContext(_localctx);
				enterOuterAlt(_localctx, 23);
				{
				setState(86);
				match(BRANCHEQ);
				setState(87);
				((BranchQContext)_localctx).input1 = match(REGISTER);
				setState(88);
				((BranchQContext)_localctx).input2 = match(REGISTER);
				setState(89);
				((BranchQContext)_localctx).label = match(LABEL);
				}
				break;
			case BRANCHLESSEQ:
				_localctx = new BranchLessQContext(_localctx);
				enterOuterAlt(_localctx, 24);
				{
				setState(90);
				match(BRANCHLESSEQ);
				setState(91);
				((BranchLessQContext)_localctx).input1 = match(REGISTER);
				setState(92);
				((BranchLessQContext)_localctx).input2 = match(REGISTER);
				setState(93);
				((BranchLessQContext)_localctx).label = match(LABEL);
				}
				break;
			case LABEL:
				_localctx = new LabelContext(_localctx);
				enterOuterAlt(_localctx, 25);
				{
				setState(94);
				match(LABEL);
				setState(95);
				match(COL);
				}
				break;
			case JUMPALABEL:
				_localctx = new JumpALContext(_localctx);
				enterOuterAlt(_localctx, 26);
				{
				setState(96);
				match(JUMPALABEL);
				setState(97);
				((JumpALContext)_localctx).label = match(LABEL);
				}
				break;
			case JUMPREG:
				_localctx = new JumpRegContext(_localctx);
				enterOuterAlt(_localctx, 27);
				{
				setState(98);
				match(JUMPREG);
				setState(99);
				((JumpRegContext)_localctx).input1 = match(REGISTER);
				}
				break;
			case PRINT:
				_localctx = new PrintContext(_localctx);
				enterOuterAlt(_localctx, 28);
				{
				setState(100);
				match(PRINT);
				setState(101);
				((PrintContext)_localctx).input1 = match(REGISTER);
				setState(102);
				((PrintContext)_localctx).input2 = match(NUMBER);
				}
				break;
			case HALT:
				_localctx = new HaltContext(_localctx);
				enterOuterAlt(_localctx, 29);
				{
				setState(103);
				match(HALT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3$m\4\2\t\2\4\3\t\3"+
		"\3\2\7\2\b\n\2\f\2\16\2\13\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3k\n\3\3"+
		"\3\2\2\4\2\4\2\3\3\2!\"\2\u0087\2\t\3\2\2\2\4j\3\2\2\2\6\b\5\4\3\2\7\6"+
		"\3\2\2\2\b\13\3\2\2\2\t\7\3\2\2\2\t\n\3\2\2\2\n\3\3\2\2\2\13\t\3\2\2\2"+
		"\f\r\7\3\2\2\rk\t\2\2\2\16k\7\4\2\2\17\20\7\5\2\2\20\21\7\"\2\2\21\22"+
		"\7\"\2\2\22k\7\"\2\2\23\24\7\6\2\2\24\25\7\"\2\2\25\26\7\"\2\2\26k\7!"+
		"\2\2\27\30\7\7\2\2\30\31\7\"\2\2\31k\7\"\2\2\32\33\7\t\2\2\33\34\7\"\2"+
		"\2\34k\7\"\2\2\35\36\7\n\2\2\36\37\7\"\2\2\37 \7\"\2\2 k\7\"\2\2!\"\7"+
		"\13\2\2\"#\7\"\2\2#$\7\"\2\2$k\7\"\2\2%&\7\f\2\2&\'\7\"\2\2\'(\7\"\2\2"+
		"(k\7\"\2\2)*\7\r\2\2*+\7\"\2\2+,\7\"\2\2,k\7\"\2\2-.\7\16\2\2./\7\"\2"+
		"\2/\60\7\"\2\2\60k\7\"\2\2\61\62\7\b\2\2\62k\7\"\2\2\63\64\7\17\2\2\64"+
		"\65\7\"\2\2\65\66\7\"\2\2\66k\7\"\2\2\678\7\20\2\289\7\"\2\29:\7\"\2\2"+
		":k\7\"\2\2;<\7\21\2\2<=\7\"\2\2=>\7\"\2\2>k\7\"\2\2?@\7\22\2\2@A\7\"\2"+
		"\2AB\7\"\2\2Bk\7\"\2\2CD\7\23\2\2DE\7\"\2\2EF\7\"\2\2Fk\7\"\2\2GH\7\24"+
		"\2\2HI\7\"\2\2IJ\7\"\2\2Jk\7\"\2\2KL\7\25\2\2LM\7\"\2\2MN\7\"\2\2Nk\7"+
		"!\2\2OP\7\26\2\2PQ\7\"\2\2QR\7\"\2\2Rk\7!\2\2ST\7\27\2\2TU\7\"\2\2Uk\7"+
		"!\2\2VW\7\30\2\2Wk\7 \2\2XY\7\31\2\2YZ\7\"\2\2Z[\7\"\2\2[k\7 \2\2\\]\7"+
		"\32\2\2]^\7\"\2\2^_\7\"\2\2_k\7 \2\2`a\7 \2\2ak\7\37\2\2bc\7\33\2\2ck"+
		"\7 \2\2de\7\34\2\2ek\7\"\2\2fg\7\35\2\2gh\7\"\2\2hk\7!\2\2ik\7\36\2\2"+
		"j\f\3\2\2\2j\16\3\2\2\2j\17\3\2\2\2j\23\3\2\2\2j\27\3\2\2\2j\32\3\2\2"+
		"\2j\35\3\2\2\2j!\3\2\2\2j%\3\2\2\2j)\3\2\2\2j-\3\2\2\2j\61\3\2\2\2j\63"+
		"\3\2\2\2j\67\3\2\2\2j;\3\2\2\2j?\3\2\2\2jC\3\2\2\2jG\3\2\2\2jK\3\2\2\2"+
		"jO\3\2\2\2jS\3\2\2\2jV\3\2\2\2jX\3\2\2\2j\\\3\2\2\2j`\3\2\2\2jb\3\2\2"+
		"\2jd\3\2\2\2jf\3\2\2\2ji\3\2\2\2k\5\3\2\2\2\4\tj";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}