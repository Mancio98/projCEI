grammar AVM;

@lexer::members {

public int lexicalErrors=0;
}

/*------------------------------------------------------------------
* PARSER RULES
*------------------------------------------------------------------*/

assembly: (instruction)* ;



instruction:
			PUSH input1 = (REGISTER|NUMBER) 									#Push
		| 	POP 																#Pop
		| 	ADD input1 = REGISTER input2 = REGISTER output = REGISTER 			#Add
		| 	ADDI input1 = REGISTER input2 = REGISTER input3 = NUMBER 			#Addi
		| 	MOVE input1 = REGISTER input2 = REGISTER 							#Move
		| 	NOT input1 = REGISTER input2 = REGISTER 							#Not
		| 	SUB input1 = REGISTER input2 = REGISTER output = REGISTER 			#Sub
		| 	MULT input1 = REGISTER input2 = REGISTER output = REGISTER 			#Mult
		| 	DIV input1 = REGISTER input2 = REGISTER output = REGISTER 			#Div
		| 	AND input1 = REGISTER input2 = REGISTER output = REGISTER 			#And
		| 	OR input1 = REGISTER input2 = REGISTER output = REGISTER 			#Or
		| 	TRANSFER input1 = REGISTER 											#Transfer
		| 	EQUAL input1 = REGISTER input2 = REGISTER output = REGISTER 		#Equal
		| 	NOTEQUAL input1 = REGISTER input2 = REGISTER output = REGISTER 		#Notequal
		| 	GREATER input1 = REGISTER input2 = REGISTER output = REGISTER 		#Greater
		| 	GREATEROREQUAL input1 = REGISTER input2 = REGISTER output = REGISTER #GreaterOrEq
		| 	LESS input1 = REGISTER input2 = REGISTER output = REGISTER 			#Less
		| 	LESSOREQUAL input1 = REGISTER input2 = REGISTER output = REGISTER 	#LessOrEq
		| 	STOREW input1 = REGISTER input2 = REGISTER offset=NUMBER 			#StoreW
		| 	LOADW input1 = REGISTER input2 = REGISTER offset=NUMBER 			#LoadW
		| 	LOADI input1 = REGISTER input2 = NUMBER 							#Loadi
		| 	BRANCH label = LABEL 												#Branch
		| 	BRANCHEQ input1 = REGISTER input2 = REGISTER label=LABEL 			#BranchQ
		| 	BRANCHLESSEQ input1 = REGISTER input2 = REGISTER label=LABEL 		#BranchLessQ
		| 	LABEL COL 															#Label
		| 	JUMPALABEL label = LABEL 											#JumpAL
		| 	JUMPREG input1=REGISTER 											#JumpReg
		| 	PRINT input1 = REGISTER 											#Print
		| 	HALT 																#Halt
		;

/*------------------------------------------------------------------
* LEXER RULES
*------------------------------------------------------------------*/



PUSH : 'push'; // pushes constant in the stack
POP : 'pop' ; // pops from stack
ADD : 'add' ; // add two values from the stack
ADDI : 'addi' ;
MOVE : 'move' ;
TRANSFER : 'transf';
NOT : 'not' ;
SUB : 'sub' ; // add two values from the stack
MULT : 'mult' ; // add two values from the stack
DIV : 'div' ; // add two values from the stack
AND : 'and' ; // AND INSTRUCTION
OR : 'or' ; // OR INSTRUCTION
EQUAL : 'eq' ; //EQUAL INSTRUCTION
NOTEQUAL: 'ne' ; //NOT EQUAL INSTRUCTION
GREATER : 'gre' ; //GREATER INSTRUCTION
GREATEROREQUAL : 'goe' ; // GREATER OR EQUAL INSTRUCTION
LESS : 'less' ; // LESS INSTRUCTION
LESSOREQUAL : 'loe' ; //LESS OR EQUAL INSTRUCTION
STOREW : 'sw' ; // store in the memory cell pointed by top the value next
LOADW : 'lw' ;
LOADI : 'li' ;
BRANCH : 'b' ; // jump to label
BRANCHEQ: 'beq' ; // jump to label if top == next
BRANCHLESSEQ:'bleq' ;
JUMPALABEL: 'jal' ; // jump to label if top <= next
JUMPREG: 'jr';
PRINT : 'print' ;// print top of stack
HALT : 'halt' ; // stop execution



COL : ':' ;
LABEL : ('a'..'z'|'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')* ;
NUMBER : '0' | ('-')?(('1'..'9')('0'..'9')*) ;



//REGISTERS
REGISTER : '$a0'|'$t1'|'$sp'|'$fp'|'$al'|'$ra'|'$hp';



WHITESP : ( '\t' | ' ' | '\r' | '\n' )+ -> channel(HIDDEN);



ERR : . { System.err.println("Invalid char: "+ getText()); lexicalErrors++;} -> channel(HIDDEN);
