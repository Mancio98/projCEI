// Generated from AVM.g4 by ANTLR 4.7.1
package parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AVMLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"PUSH", "POP", "ADD", "ADDI", "MOVE", "TRANSFER", "NOT", "SUB", "MULT", 
		"DIV", "AND", "OR", "EQUAL", "NOTEQUAL", "GREATER", "GREATEROREQUAL", 
		"LESS", "LESSOREQUAL", "STOREW", "LOADW", "LOADI", "BRANCH", "BRANCHEQ", 
		"BRANCHLESSEQ", "JUMPALABEL", "JUMPREG", "PRINT", "HALT", "COL", "LABEL", 
		"NUMBER", "REGISTER", "WHITESP", "ERR"
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



	public int lexicalErrors=0;


	public AVMLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "AVM.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 33:
			ERR_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void ERR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 System.err.println("Invalid char: "+ getText()); lexicalErrors++;
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2$\u00f3\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3"+
		"\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3"+
		"\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3"+
		"\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3"+
		"\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\37\3"+
		"\37\7\37\u00bf\n\37\f\37\16\37\u00c2\13\37\3 \3 \5 \u00c6\n \3 \3 \7 "+
		"\u00ca\n \f \16 \u00cd\13 \5 \u00cf\n \3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3"+
		"!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\5!\u00e6\n!\3\"\6\"\u00e9\n\"\r\"\16\""+
		"\u00ea\3\"\3\"\3#\3#\3#\3#\3#\2\2$\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n"+
		"\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30"+
		"/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$\3\2\5\4\2C\\c|\5\2\62"+
		";C\\c|\5\2\13\f\17\17\"\"\2\u00fd\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2"+
		"\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2"+
		"\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2"+
		"\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2"+
		"\2\2C\3\2\2\2\2E\3\2\2\2\3G\3\2\2\2\5L\3\2\2\2\7P\3\2\2\2\tT\3\2\2\2\13"+
		"Y\3\2\2\2\r^\3\2\2\2\17e\3\2\2\2\21i\3\2\2\2\23m\3\2\2\2\25r\3\2\2\2\27"+
		"v\3\2\2\2\31z\3\2\2\2\33}\3\2\2\2\35\u0080\3\2\2\2\37\u0083\3\2\2\2!\u0087"+
		"\3\2\2\2#\u008b\3\2\2\2%\u0090\3\2\2\2\'\u0094\3\2\2\2)\u0097\3\2\2\2"+
		"+\u009a\3\2\2\2-\u009d\3\2\2\2/\u009f\3\2\2\2\61\u00a3\3\2\2\2\63\u00a8"+
		"\3\2\2\2\65\u00ac\3\2\2\2\67\u00af\3\2\2\29\u00b5\3\2\2\2;\u00ba\3\2\2"+
		"\2=\u00bc\3\2\2\2?\u00ce\3\2\2\2A\u00e5\3\2\2\2C\u00e8\3\2\2\2E\u00ee"+
		"\3\2\2\2GH\7r\2\2HI\7w\2\2IJ\7u\2\2JK\7j\2\2K\4\3\2\2\2LM\7r\2\2MN\7q"+
		"\2\2NO\7r\2\2O\6\3\2\2\2PQ\7c\2\2QR\7f\2\2RS\7f\2\2S\b\3\2\2\2TU\7c\2"+
		"\2UV\7f\2\2VW\7f\2\2WX\7k\2\2X\n\3\2\2\2YZ\7o\2\2Z[\7q\2\2[\\\7x\2\2\\"+
		"]\7g\2\2]\f\3\2\2\2^_\7v\2\2_`\7t\2\2`a\7c\2\2ab\7p\2\2bc\7u\2\2cd\7h"+
		"\2\2d\16\3\2\2\2ef\7p\2\2fg\7q\2\2gh\7v\2\2h\20\3\2\2\2ij\7u\2\2jk\7w"+
		"\2\2kl\7d\2\2l\22\3\2\2\2mn\7o\2\2no\7w\2\2op\7n\2\2pq\7v\2\2q\24\3\2"+
		"\2\2rs\7f\2\2st\7k\2\2tu\7x\2\2u\26\3\2\2\2vw\7c\2\2wx\7p\2\2xy\7f\2\2"+
		"y\30\3\2\2\2z{\7q\2\2{|\7t\2\2|\32\3\2\2\2}~\7g\2\2~\177\7s\2\2\177\34"+
		"\3\2\2\2\u0080\u0081\7p\2\2\u0081\u0082\7g\2\2\u0082\36\3\2\2\2\u0083"+
		"\u0084\7i\2\2\u0084\u0085\7t\2\2\u0085\u0086\7g\2\2\u0086 \3\2\2\2\u0087"+
		"\u0088\7i\2\2\u0088\u0089\7q\2\2\u0089\u008a\7g\2\2\u008a\"\3\2\2\2\u008b"+
		"\u008c\7n\2\2\u008c\u008d\7g\2\2\u008d\u008e\7u\2\2\u008e\u008f\7u\2\2"+
		"\u008f$\3\2\2\2\u0090\u0091\7n\2\2\u0091\u0092\7q\2\2\u0092\u0093\7g\2"+
		"\2\u0093&\3\2\2\2\u0094\u0095\7u\2\2\u0095\u0096\7y\2\2\u0096(\3\2\2\2"+
		"\u0097\u0098\7n\2\2\u0098\u0099\7y\2\2\u0099*\3\2\2\2\u009a\u009b\7n\2"+
		"\2\u009b\u009c\7k\2\2\u009c,\3\2\2\2\u009d\u009e\7d\2\2\u009e.\3\2\2\2"+
		"\u009f\u00a0\7d\2\2\u00a0\u00a1\7g\2\2\u00a1\u00a2\7s\2\2\u00a2\60\3\2"+
		"\2\2\u00a3\u00a4\7d\2\2\u00a4\u00a5\7n\2\2\u00a5\u00a6\7g\2\2\u00a6\u00a7"+
		"\7s\2\2\u00a7\62\3\2\2\2\u00a8\u00a9\7l\2\2\u00a9\u00aa\7c\2\2\u00aa\u00ab"+
		"\7n\2\2\u00ab\64\3\2\2\2\u00ac\u00ad\7l\2\2\u00ad\u00ae\7t\2\2\u00ae\66"+
		"\3\2\2\2\u00af\u00b0\7r\2\2\u00b0\u00b1\7t\2\2\u00b1\u00b2\7k\2\2\u00b2"+
		"\u00b3\7p\2\2\u00b3\u00b4\7v\2\2\u00b48\3\2\2\2\u00b5\u00b6\7j\2\2\u00b6"+
		"\u00b7\7c\2\2\u00b7\u00b8\7n\2\2\u00b8\u00b9\7v\2\2\u00b9:\3\2\2\2\u00ba"+
		"\u00bb\7<\2\2\u00bb<\3\2\2\2\u00bc\u00c0\t\2\2\2\u00bd\u00bf\t\3\2\2\u00be"+
		"\u00bd\3\2\2\2\u00bf\u00c2\3\2\2\2\u00c0\u00be\3\2\2\2\u00c0\u00c1\3\2"+
		"\2\2\u00c1>\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c3\u00cf\7\62\2\2\u00c4\u00c6"+
		"\7/\2\2\u00c5\u00c4\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7"+
		"\u00cb\4\63;\2\u00c8\u00ca\4\62;\2\u00c9\u00c8\3\2\2\2\u00ca\u00cd\3\2"+
		"\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00cf\3\2\2\2\u00cd"+
		"\u00cb\3\2\2\2\u00ce\u00c3\3\2\2\2\u00ce\u00c5\3\2\2\2\u00cf@\3\2\2\2"+
		"\u00d0\u00d1\7&\2\2\u00d1\u00d2\7c\2\2\u00d2\u00e6\7\62\2\2\u00d3\u00d4"+
		"\7&\2\2\u00d4\u00d5\7v\2\2\u00d5\u00e6\7\63\2\2\u00d6\u00d7\7&\2\2\u00d7"+
		"\u00d8\7u\2\2\u00d8\u00e6\7r\2\2\u00d9\u00da\7&\2\2\u00da\u00db\7h\2\2"+
		"\u00db\u00e6\7r\2\2\u00dc\u00dd\7&\2\2\u00dd\u00de\7c\2\2\u00de\u00e6"+
		"\7n\2\2\u00df\u00e0\7&\2\2\u00e0\u00e1\7t\2\2\u00e1\u00e6\7c\2\2\u00e2"+
		"\u00e3\7&\2\2\u00e3\u00e4\7j\2\2\u00e4\u00e6\7r\2\2\u00e5\u00d0\3\2\2"+
		"\2\u00e5\u00d3\3\2\2\2\u00e5\u00d6\3\2\2\2\u00e5\u00d9\3\2\2\2\u00e5\u00dc"+
		"\3\2\2\2\u00e5\u00df\3\2\2\2\u00e5\u00e2\3\2\2\2\u00e6B\3\2\2\2\u00e7"+
		"\u00e9\t\4\2\2\u00e8\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00e8\3\2"+
		"\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ed\b\"\2\2\u00ed"+
		"D\3\2\2\2\u00ee\u00ef\13\2\2\2\u00ef\u00f0\b#\3\2\u00f0\u00f1\3\2\2\2"+
		"\u00f1\u00f2\b#\2\2\u00f2F\3\2\2\2\t\2\u00c0\u00c5\u00cb\u00ce\u00e5\u00ea"+
		"\4\2\3\2\3#\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}