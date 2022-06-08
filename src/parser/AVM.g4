grammar AVM;

@header {

import java.util.HashMap;
}

@lexer::members {

public int lexicalErrors=0;
}

/*------------------------------------------------------------------
 * PARSER RULES

 *------------------------------------------------------------------*/
  
assembly: (instruction)* ;

instruction:
    ( PUSH n=NUMBER 
	  | PUSH l=LABEL 		     
	  | POP		    
	  | ADD		    
	  | SUB		    
	  | MULT	    
	  | DIV
	  | AND
	  | OR
	  | EQUAL
	  | NOTEQUAL
	  | GREATER
	  | LESS
	  | LESSOREQUAL		    
	  | STOREW	  
	  | LOADW           
	  | l=LABEL COL     
	  | BRANCH l=LABEL  
	  | BRANCHEQ l=LABEL 
	  | BRANCHLESSEQ l=LABEL 
	  | JS              
	  | LOADRA          
	  | STORERA         
	  | LOADRV          
	  | STORERV         
	  | LOADFP          
	  | STOREFP         
	  | COPYFP          
	  | LOADHP          
	  | STOREHP         
	  | PRINT           
	  | HALT
	  ) ;
 	 
/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/
 
PUSH	: 'push' ; 	// pushes constant in the stack
POP		: 'pop' ; 	// pops from stack
ADD	 	: 'add' input1 = REGISTER input2 = REGISTER output = REGISTER;  	// add two values from the stack
SUB	 	: 'sub' input1 = REGISTER input2 = REGISTER output = REGISTER ;	// add two values from the stack
MULT 	: 'mult' input1 = REGISTER input2 = REGISTER output = REGISTER;  	// add two values from the stack
DIV	 	: 'div' input1 = REGISTER input2 = REGISTER output = REGISTER;	// add two values from the stack
AND		: 'and' input1 = REGISTER input2 = REGISTER output = REGISTER;	// AND INSTRUCTION
OR		: 'or' input1 = REGISTER input2 = REGISTER output = REGISTER;	// OR INSTRUCTION
EQUAL 	: 'eq' input1 = REGISTER input2 = REGISTER output = REGISTER;		//EQUAL INSTRUCTION
NOTEQUAL	: 'ne' input1 = REGISTER input2 = REGISTER output = REGISTER;	//NOT EQUAL INSTRUCTION
GREATER : 'gre' input1 = REGISTER input2 = REGISTER output = REGISTER;	//GREATER INSTRUCTION
GREATEROREQUAL	: 'goe' input1 = REGISTER input2 = REGISTER output = REGISTER; 	// GREATER OR EQUAL INSTRUCTION
LESS	: 'less' input1 = REGISTER input2 = REGISTER output = REGISTER;	// LESS INSTRUCTION
LESSOREQUAL		: 'loe' input1 = REGISTER input2 = REGISTER output = REGISTER;	//LESS OR EQUAL INSTRUCTION	
STOREW	: 'sw' ; 	// store in the memory cell pointed by top the value next
LOADW	: 'lw' ;	// load a value from the memory cell pointed by top
BRANCH	: 'b' ;	// jump to label
BRANCHEQ: 'beq' ;	// jump to label if top == next
BRANCHLESSEQ:'bleq' ;	// jump to label if top <= next
JS	 	: 'js' ;	// jump to instruction pointed by top of stack and store next instruction in ra
LOADRA	: 'lra' ;	// load from ra
STORERA	: 'sra' ;	// store top into ra	 
LOADRV	: 'lrv' ;	// load from rv
STORERV	: 'srv' ;	// store top into rv	 
LOADFP	: 'lfp' ;	// load frame pointer in the stack
STOREFP	: 'sfp' ;	// store top into frame pointer
COPYFP	: 'cfp' ;  // copy stack pointer into frame pointer
LOADHP	: 'lhp' ;	// load heap pointer in the stack
STOREHP	: 'shp' ;	// store top into heap pointer
PRINT	: 'print' ;// print top of stack
HALT	: 'halt' ;	// stop execution

COL		: ':' ;
LABEL	: ('a'..'z'|'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')* ;
NUMBER	: '0' | ('-')?(('1'..'9')('0'..'9')*) ;

//REGISTERS
REGISTER:
	'$a0'  //results in the accumulators
	| '$t1' //tmp register
	| '$sp' //top of the stack
	| '$fp' //points to al relative to the active frame
	| '$al' //static chain for scopes
	| '$ra' //return address where the address of the next instruction is saved
	| '$hp' //pointer for the heap
	| '$cl' //control link, points at the previous frame (basically is the previous sp)
	;

WHITESP	: ( '\t' | ' ' | '\r' | '\n' )+   -> channel(HIDDEN);

ERR		: . { System.err.println("Invalid char: "+ getText()); lexicalErrors++;  } -> channel(HIDDEN); 

