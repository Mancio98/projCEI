package Interpreter;

import java.util.ArrayList;
import java.util.HashMap;

import ast.LineCode;
import parser.AVMParser;

public class ExecuteVM {
    
    public static final int CODESIZE = 10000;
    public static final int MEMSIZE = 10000;
 
    private ArrayList<LineCode> code;
    private int[] stack = new int[MEMSIZE];
    
    private int ip = 0;
    private int hp = 0;
   
    HashMap<String,Integer> registers = new HashMap<String,Integer>();
   
    
    public ExecuteVM(ArrayList<LineCode> code) {
      this.code = code;
      
      registers.put("sp", MEMSIZE);
      
      registers.put("fp", MEMSIZE);
      registers.put("ra", 0);
      registers.put("a0", 0);
      registers.put("t1", 0);
     
    }
    
    public void cpu() {
      while ( true ) {
    	  
    	if(hp+1>=registers.get("sp")) {
    		System.out.println("\nError: Out of memory");
            return;
    	}
    	else {
    		LineCode bytecode = code.get(ip++); // fetch
            String[] args = bytecode.getArgs();
            String arg = bytecode.getArg();
            int offset = bytecode.getOffset();
            
            switch (bytecode.getCommand()) {
              case AVMParser.PUSH:
            	  
            	  registers.put("sp", registers.get("sp")-1);
            	  stack[registers.get("sp")] = registers.get(arg);
            	  
                break;
              case AVMParser.POP:
            	  registers.put("sp", registers.get("sp")+1);
            	  
                break;
              case AVMParser.ADD :
               
            	  int sum = registers.get(args[0]) + registers.get(args[1]);
            	  registers.put(args[2],sum); 
                break;
              case AVMParser.ADDI :
                 
            	  int sumi = registers.get(args[1]) + registers.get(args[2]);
            	  registers.put(args[0], sumi);
                  break;
              case AVMParser.AND :
                  
            	  if(registers.get(args[0]) == 0)
            		  registers.put(args[2], 0);
            	  else
            		  registers.put(args[2], registers.get(args[1]));
            	 
            	 
                  break;
              case AVMParser.MOVE :
            	  
			                  
                  break;
              case AVMParser.NOT :
			    
			    break;
              case AVMParser.OR :
  			    
  			    break;
              case AVMParser.MULT :
               
                break;
              case AVMParser.DIV :
                
                break;
              case AVMParser.SUB :
               
                break;
              case AVMParser.STOREW : //
                   
                break;
              case AVMParser.LOADW : //
            	
                break;
              case AVMParser.LOADI :
  			    
  			    break;
              case AVMParser.BRANCH : 
                
                break;
              case AVMParser.BRANCHEQ : //
                
                break;
              case AVMParser.BRANCHLESSEQ :
               
                break;
              case AVMParser.EQUAL :
                  
                  break;
              case AVMParser.NOTEQUAL :
                  
                  break;
              case AVMParser.GREATEROREQUAL:
            	  break;
              case AVMParser.GREATER:
            	  break;
              case AVMParser.JUMPALABEL:
            	  break;
             case AVMParser.PRINT :
                System.out.println((sp<MEMSIZE)?memory[sp]:"Empty stack!");
                break;
             case AVMParser.HALT :
            	//to print the result 
             	System.out.println("\nResult: " + memory[sp] + "\n");
             	return;
            }
    	} 
      }
    } 
    
    private int pop() {
      return memory[sp++];
    }
    
    private void push(LineCode v) {
      memory[--sp] = v;
    }
    
}