package Interpreter;

import java.util.ArrayList;
import java.util.HashMap;

import parser.AVMParser;
import util.LineCode;

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
            	  
			      registers.put(args[0], registers.get(args[1]));            
                  break;
              case AVMParser.NOT :
			    
            	  registers.put(args[0], (registers.get(args[1]) == 1) ? 0 : 1);
			    break;
			    
              case AVMParser.OR :
  			    
            	  if(registers.get(args[0]) == 1)
            		  registers.put(args[2], 1);
            	  else
            		  registers.put(args[2], registers.get(args[1]));
            	  
  			    break;
              case AVMParser.MULT :
               
            	  int mult = registers.get(args[0]) * registers.get(args[1]);
            	  registers.put(args[2],mult); 
                break;
              case AVMParser.DIV :
                
            	  int div = registers.get(args[0]) / registers.get(args[1]);
            	  registers.put(args[2], div); 
                break;
              case AVMParser.SUB :
               
            	  int sub = registers.get(args[0]) - registers.get(args[1]);
            	  registers.put(args[2], sub); 
                break;
              case AVMParser.STOREW : 
                   int possw = registers.get("al")+offset;
                   stack[possw] = registers.get(args[0]);
                break;
                
              case AVMParser.LOADW : 
            	  int poslw = registers.get("al")+offset;
                  registers.put(args[0], stack[poslw]);
                break;
                
              case AVMParser.LOADI :
            	  registers.put(args[0], Integer.parseInt(args[1]));
  			    break;
  			    
              case AVMParser.BRANCH : 
                
            	  ip = Integer.parseInt(arg);
           
                break;
              case AVMParser.BRANCHEQ :
                if(registers.get(args[0])==registers.get(args[1]))
                	ip = Integer.parseInt(args[2]);
                break;
                
              case AVMParser.BRANCHLESSEQ :
            	  if(registers.get(args[0])<=registers.get(args[1]))
              		ip = Integer.parseInt(args[2]);
                break;
              case AVMParser.EQUAL :
            	  if(registers.get(args[0]) == registers.get(args[1]))
            		  registers.put(args[2],1);
            	  else
            		  registers.put(args[2],0);
                  break;
              case AVMParser.NOTEQUAL :
            	  if(registers.get(args[0]) != registers.get(args[1]))
            		  registers.put(args[2],1);
            	  else
            		  registers.put(args[2],0);
                  break;
                  
              case AVMParser.GREATEROREQUAL:
            	  if(registers.get(args[0]) >= registers.get(args[1]))
            		  registers.put(args[2],1);
            	  else
            		  registers.put(args[2],0);
            	  break;
              case AVMParser.GREATER:
            	  if(registers.get(args[0]) > registers.get(args[1]))
            		  registers.put(args[2],1);
            	  else
            		  registers.put(args[2],0);
            	  break;
              case AVMParser.LESS:
            	  if(registers.get(args[0]) < registers.get(args[1]))
            		  registers.put(args[2],1);
            	  else
            		  registers.put(args[2],0);
            	  break;
              case AVMParser.LESSOREQUAL:
            	  if(registers.get(args[0]) <= registers.get(args[1]))
            		  registers.put(args[2],1);
            	  else
            		  registers.put(args[2],0);
            	  break;
              case AVMParser.JUMPALABEL:
            	  
            	  registers.put("ra", ip);
            	  ip = Integer.parseInt(arg);
            	  break;
            	  
              case AVMParser.TRANSFER:
            	  stack[hp] = registers.get(arg);
            	  break;
              case AVMParser.JUMPREG:
            	  ip = registers.get("ra");
              case AVMParser.PRINT :
                System.out.println((registers.get("sp")<MEMSIZE) ? stack[registers.get("sp")]:"Empty stack!");
                break;
              case AVMParser.HALT :
            	//to print the result 
             	System.out.println("\nResult: " + stack[registers.get("sp")] + "\n");
             	return;
            }
    	} 
      }
    } 
    
    
}