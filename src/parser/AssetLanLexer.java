// Generated from AssetLan.g4 by ANTLR 4.7.1
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
public class AssetLanLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, BOOL=33, ID=34, NUMBER=35, WS=36, LINECOMMENTS=37, BLOCKCOMMENTS=38;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "BOOL", 
		"CHAR", "ID", "DIGIT", "NUMBER", "WS", "LINECOMMENTS", "BLOCKCOMMENTS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "';'", "'asset'", "'void'", "'('", "')'", "'['", "']'", "'{'", 
		"'}'", "','", "'int'", "'bool'", "'-o'", "'print'", "'transfer'", "'return'", 
		"'if'", "'else'", "'*'", "'/'", "'+'", "'-'", "'!'", "'<'", "'<='", "'>'", 
		"'>='", "'=='", "'!='", "'&&'", "'||'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, "BOOL", "ID", "NUMBER", 
		"WS", "LINECOMMENTS", "BLOCKCOMMENTS"
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


	public AssetLanLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "AssetLan.g4"; }

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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2(\u00f8\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\3\2\3\2\3\3\3"+
		"\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b"+
		"\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23"+
		"\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30"+
		"\3\31\3\31\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\36\3\36"+
		"\3\36\3\37\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\""+
		"\3\"\5\"\u00c4\n\"\3#\3#\3$\3$\3$\7$\u00cb\n$\f$\16$\u00ce\13$\3%\3%\3"+
		"&\6&\u00d3\n&\r&\16&\u00d4\3\'\3\'\3\'\3\'\3(\3(\3(\3(\7(\u00df\n(\f("+
		"\16(\u00e2\13(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3)\7)\u00ef\n)\f)\16)\u00f2"+
		"\13)\3)\3)\3)\3)\3)\2\2*\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f"+
		"\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63"+
		"\33\65\34\67\359\36;\37= ?!A\"C#E\2G$I\2K%M&O\'Q(\3\2\b\4\2C\\c|\5\2\13"+
		"\f\17\17\"\"\4\2\f\f\17\17\4\2,,\61\61\3\2,,\3\2\61\61\2\u00fe\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
		"\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3"+
		"\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3"+
		"\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2"+
		"=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2G\3\2\2\2\2K\3\2\2\2\2M\3"+
		"\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\3S\3\2\2\2\5U\3\2\2\2\7W\3\2\2\2\t]\3\2\2"+
		"\2\13b\3\2\2\2\rd\3\2\2\2\17f\3\2\2\2\21h\3\2\2\2\23j\3\2\2\2\25l\3\2"+
		"\2\2\27n\3\2\2\2\31p\3\2\2\2\33t\3\2\2\2\35y\3\2\2\2\37|\3\2\2\2!\u0082"+
		"\3\2\2\2#\u008b\3\2\2\2%\u0092\3\2\2\2\'\u0095\3\2\2\2)\u009a\3\2\2\2"+
		"+\u009c\3\2\2\2-\u009e\3\2\2\2/\u00a0\3\2\2\2\61\u00a2\3\2\2\2\63\u00a4"+
		"\3\2\2\2\65\u00a6\3\2\2\2\67\u00a9\3\2\2\29\u00ab\3\2\2\2;\u00ae\3\2\2"+
		"\2=\u00b1\3\2\2\2?\u00b4\3\2\2\2A\u00b7\3\2\2\2C\u00c3\3\2\2\2E\u00c5"+
		"\3\2\2\2G\u00c7\3\2\2\2I\u00cf\3\2\2\2K\u00d2\3\2\2\2M\u00d6\3\2\2\2O"+
		"\u00da\3\2\2\2Q\u00e5\3\2\2\2ST\7?\2\2T\4\3\2\2\2UV\7=\2\2V\6\3\2\2\2"+
		"WX\7c\2\2XY\7u\2\2YZ\7u\2\2Z[\7g\2\2[\\\7v\2\2\\\b\3\2\2\2]^\7x\2\2^_"+
		"\7q\2\2_`\7k\2\2`a\7f\2\2a\n\3\2\2\2bc\7*\2\2c\f\3\2\2\2de\7+\2\2e\16"+
		"\3\2\2\2fg\7]\2\2g\20\3\2\2\2hi\7_\2\2i\22\3\2\2\2jk\7}\2\2k\24\3\2\2"+
		"\2lm\7\177\2\2m\26\3\2\2\2no\7.\2\2o\30\3\2\2\2pq\7k\2\2qr\7p\2\2rs\7"+
		"v\2\2s\32\3\2\2\2tu\7d\2\2uv\7q\2\2vw\7q\2\2wx\7n\2\2x\34\3\2\2\2yz\7"+
		"/\2\2z{\7q\2\2{\36\3\2\2\2|}\7r\2\2}~\7t\2\2~\177\7k\2\2\177\u0080\7p"+
		"\2\2\u0080\u0081\7v\2\2\u0081 \3\2\2\2\u0082\u0083\7v\2\2\u0083\u0084"+
		"\7t\2\2\u0084\u0085\7c\2\2\u0085\u0086\7p\2\2\u0086\u0087\7u\2\2\u0087"+
		"\u0088\7h\2\2\u0088\u0089\7g\2\2\u0089\u008a\7t\2\2\u008a\"\3\2\2\2\u008b"+
		"\u008c\7t\2\2\u008c\u008d\7g\2\2\u008d\u008e\7v\2\2\u008e\u008f\7w\2\2"+
		"\u008f\u0090\7t\2\2\u0090\u0091\7p\2\2\u0091$\3\2\2\2\u0092\u0093\7k\2"+
		"\2\u0093\u0094\7h\2\2\u0094&\3\2\2\2\u0095\u0096\7g\2\2\u0096\u0097\7"+
		"n\2\2\u0097\u0098\7u\2\2\u0098\u0099\7g\2\2\u0099(\3\2\2\2\u009a\u009b"+
		"\7,\2\2\u009b*\3\2\2\2\u009c\u009d\7\61\2\2\u009d,\3\2\2\2\u009e\u009f"+
		"\7-\2\2\u009f.\3\2\2\2\u00a0\u00a1\7/\2\2\u00a1\60\3\2\2\2\u00a2\u00a3"+
		"\7#\2\2\u00a3\62\3\2\2\2\u00a4\u00a5\7>\2\2\u00a5\64\3\2\2\2\u00a6\u00a7"+
		"\7>\2\2\u00a7\u00a8\7?\2\2\u00a8\66\3\2\2\2\u00a9\u00aa\7@\2\2\u00aa8"+
		"\3\2\2\2\u00ab\u00ac\7@\2\2\u00ac\u00ad\7?\2\2\u00ad:\3\2\2\2\u00ae\u00af"+
		"\7?\2\2\u00af\u00b0\7?\2\2\u00b0<\3\2\2\2\u00b1\u00b2\7#\2\2\u00b2\u00b3"+
		"\7?\2\2\u00b3>\3\2\2\2\u00b4\u00b5\7(\2\2\u00b5\u00b6\7(\2\2\u00b6@\3"+
		"\2\2\2\u00b7\u00b8\7~\2\2\u00b8\u00b9\7~\2\2\u00b9B\3\2\2\2\u00ba\u00bb"+
		"\7v\2\2\u00bb\u00bc\7t\2\2\u00bc\u00bd\7w\2\2\u00bd\u00c4\7g\2\2\u00be"+
		"\u00bf\7h\2\2\u00bf\u00c0\7c\2\2\u00c0\u00c1\7n\2\2\u00c1\u00c2\7u\2\2"+
		"\u00c2\u00c4\7g\2\2\u00c3\u00ba\3\2\2\2\u00c3\u00be\3\2\2\2\u00c4D\3\2"+
		"\2\2\u00c5\u00c6\t\2\2\2\u00c6F\3\2\2\2\u00c7\u00cc\5E#\2\u00c8\u00cb"+
		"\5E#\2\u00c9\u00cb\5I%\2\u00ca\u00c8\3\2\2\2\u00ca\u00c9\3\2\2\2\u00cb"+
		"\u00ce\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cdH\3\2\2\2"+
		"\u00ce\u00cc\3\2\2\2\u00cf\u00d0\4\62;\2\u00d0J\3\2\2\2\u00d1\u00d3\5"+
		"I%\2\u00d2\u00d1\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4"+
		"\u00d5\3\2\2\2\u00d5L\3\2\2\2\u00d6\u00d7\t\3\2\2\u00d7\u00d8\3\2\2\2"+
		"\u00d8\u00d9\b\'\2\2\u00d9N\3\2\2\2\u00da\u00db\7\61\2\2\u00db\u00dc\7"+
		"\61\2\2\u00dc\u00e0\3\2\2\2\u00dd\u00df\n\4\2\2\u00de\u00dd\3\2\2\2\u00df"+
		"\u00e2\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e3\3\2"+
		"\2\2\u00e2\u00e0\3\2\2\2\u00e3\u00e4\b(\2\2\u00e4P\3\2\2\2\u00e5\u00e6"+
		"\7\61\2\2\u00e6\u00e7\7,\2\2\u00e7\u00f0\3\2\2\2\u00e8\u00ef\n\5\2\2\u00e9"+
		"\u00ea\7\61\2\2\u00ea\u00ef\n\6\2\2\u00eb\u00ec\7,\2\2\u00ec\u00ef\n\7"+
		"\2\2\u00ed\u00ef\5Q)\2\u00ee\u00e8\3\2\2\2\u00ee\u00e9\3\2\2\2\u00ee\u00eb"+
		"\3\2\2\2\u00ee\u00ed\3\2\2\2\u00ef\u00f2\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f0"+
		"\u00f1\3\2\2\2\u00f1\u00f3\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f3\u00f4\7,"+
		"\2\2\u00f4\u00f5\7\61\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00f7\b)\2\2\u00f7"+
		"R\3\2\2\2\n\2\u00c3\u00ca\u00cc\u00d4\u00e0\u00ee\u00f0\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}