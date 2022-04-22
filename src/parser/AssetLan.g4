grammar AssetLan;

// THIS IS THE PARSER INPUT

program	    : field* asset* function* initcall ; 
			// la portata di field e asset e` soltanto function

field       : type ID ('=' exp)? ';' ;

asset       : 'asset' ID ';' ;

function    : (type | 'void') ID '(' (dec)? ')' '[' (adec)? ']' 
	      '{' dec* statement* '}' ;

dec         : type ID (',' type ID)* ;

adec 	    : 'asset' ID (',' 'asset' ID)*;

statement   : assignment ';'
	    | move ';'     // sposta un asset da una parte all'altra
	    | print ';'
	    | transfer ';' // trasferisce l'asset all' utente (chi esegue il codice)
	    | ret ';'
	    | ite
	    | call ';'
	    ;

type        : 'int'
            | 'bool'
            ;

assignment  : ID '=' exp ;

move        : ID '-o' ID ;

print	    : 'print' exp;

transfer    : 'transfer' ID;

ret	   		: 'return' (exp)?;

ite         : 'if' '(' exp ')' statement ('else' statement)?;

call        : ID '(' (exp (',' exp)* )? ')' '[' (ID (',' ID)* )? ']' ;

initcall    : ID '(' (exp (',' exp)* )? ')' '[' (exp (',' exp)* )? ']' ;

exp	    : '(' exp ')'				        				#baseExp
	    | '-' exp					        				#negExp
	    | '!' exp                                           #notExp
	    | ID												#idExp
	    | left=exp op=('*' | '/')               right=exp   #binExp
	    | left=exp op=('+' | '-')               right=exp   #binExp
	    | left=exp op=('<' | '<=' | '>' | '>=') right=exp   #binExp
	    | left=exp op=('=='| '!=')              right=exp   #binExp
	    | left=exp op='&&'                      right=exp   #binExp
	    | left=exp op='||'                      right=exp   #binExp
	    | call                                              #callExp
	    | BOOL                                              #boolExp
	    | NUMBER					        				#valExp;


// THIS IS THE LEXER INPUT

//Booleans
BOOL        : 'true'|'false';

//IDs
fragment CHAR 	    : 'a'..'z' |'A'..'Z' ;
ID          : CHAR (CHAR | DIGIT)* ;

//Numbers
fragment DIGIT	    : '0'..'9';
NUMBER      : DIGIT+;

//ESCAPE SEQUENCES
WS              : (' '|'\t'|'\n'|'\r')-> skip;
LINECOMMENTS 	: '//' (~('\n'|'\r'))* -> skip;
BLOCKCOMMENTS   : '/*'( ~('/'|'*')|'/'~'*'|'*'~'/'|BLOCKCOMMENTS)* '*/' -> skip;

/*
SEMANTICA DI ASSETLAN


In AssetLan le funzioni possono essere dichiarate con asset. Ad esempio

void f(int a, bool b)[asset u, asset v]{ Body }

Quando la funzione viene invocata, ad esempio

f(5,true)[x,y]

quello che accade e` che l'asset x e y VENGONO SVUOTATI e memorizzati nei parametri 
formali u e v, rispettivamente. Quindi, a seguito dell'invocazione, i valori di x e 
di y sono 0.

Gli asset possono essere spostati SOLAMENTE 

* mediante l'operazione move x -o y il cui significato e` 
	(a) il valore di x viene sommato a quello di y e il totale memorizzato in y 
	(b) il valore di x diventa 0
  (i 2 argomenti di move devono essere 2 asset)

* mediante l'operazione transfer:  transfer x significa che
	(a) il valore di x viene trasferito al chiamante di initcall
	(b) il valore di x diventa 0

*/
