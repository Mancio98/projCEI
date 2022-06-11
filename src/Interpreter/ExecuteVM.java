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
      
      registers.put("$sp", MEMSIZE);
      
      registers.put("$fp", MEMSIZE-1);
      registers.put("$ra", null);
      registers.put("$a0", null);
      registers.put("$t1", null);
      registers.put("$al", null);
     
    }
    
    public void cpu() {
      while ( true ) {
    	  
    	if(hp+1>=registers.get("$sp")) {
    		System.out.println("\nError: Out of memory");
            return;
    	}
    	else {
    		LineCode bytecode = code.get(ip++); // fetch
            String[] args = bytecode.getArgs();
            String arg = bytecode.getArg();
            
            int offset = bytecode.getOffset();
            /*
            if(arg != "")
            	System.out.println("arg "+arg+" "+registers.get(arg)+"\n");
            for(String x : args) {
            	
            	if(registers.get(x)!= null)
            		System.out.println("args "+x+" "+registers.get(x)+"\n");
            }
            System.out.println("offset "+offset+"\n");*/
            
            System.out.println("fp "+registers.get("$fp")+"\n");
            System.out.println("sp "+registers.get("$sp")+"\n");
            System.out.println("al "+registers.get("$al")+"\n");
            System.out.println("a0 "+registers.get("$a0")+"\n");
            System.out.println("ra "+registers.get("$ra")+"\n");
            
            
            for(int i=registers.get("$sp"); i<10000;i++) {

                System.out.println(i + " : " +stack[i]);

            }
            switch (bytecode.getCommand()) {
              case AVMParser.PUSH:
            	  System.out.println("push");
            	  
            	  registers.put("$sp", registers.get("$sp")-1);
            	  stack[registers.get("$sp")] = registers.get(arg);
            	  
            	  
                break;
              case AVMParser.POP:
            	  System.out.println("pop");
            	  registers.put("$sp", registers.get("$sp")+1);
            	  
                break;
              case AVMParser.ADD :
            	  System.out.println("add");
            	  int sum = registers.get(args[0]) + registers.get(args[1]);
            	  registers.put(args[2],sum); 
                break;
              case AVMParser.ADDI :
            	  System.out.println("addi");
            	  int sumi = registers.get(args[1]) + Integer.parseInt(args[2]);
            	  registers.put(args[0], sumi);
                  break;
              case AVMParser.AND :
                  
            	  if(registers.get(args[0]) == 0)
            		  registers.put(args[2], 0);
            	  else
            		  registers.put(args[2], registers.get(args[1]));
            	 
            	 
                  break;
              case AVMParser.MOVE :
            	  
            	  System.out.println("move");
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
               
            	  System.out.println("faccio sub:");
            	  int sub = registers.get(args[0]) - registers.get(args[1]);
            	  registers.put(args[2], sub); 
  
                break;
                
              case AVMParser.STOREW : 
            	  System.out.println("sw");
                   int possw = registers.get(args[1])+offset;
                   
                   stack[possw] = registers.get(args[0]);
                break;
                
              case AVMParser.LOADW : 
            	  System.out.println("lw");
            	  int poslw = registers.get(args[1])+offset;
            	  
                  registers.put(args[0], stack[poslw]);
                  System.out.println(args[0]+": "+registers.get(args[0])+" "+poslw+"\n");       	  
                break;
                
              case AVMParser.LOADI :
            	  System.out.println("li");
            	  registers.put(args[0], Integer.parseInt(args[1]));
  			    break;
  			    
              case AVMParser.BRANCH :
            	  
            	  System.out.println("branch");
            	  ip = Integer.parseInt(arg);
           
                break;
              case AVMParser.BRANCHEQ :
            	  System.out.println("beq");
                if(registers.get(args[0])==registers.get(args[1]))
                	ip = Integer.parseInt(args[2]);
             
                break;
                
              case AVMParser.BRANCHLESSEQ :
            	  if(registers.get(args[0])<=registers.get(args[1]))
              		ip = Integer.parseInt(args[2]);
                break;
              case AVMParser.EQUAL :
            	  System.out.println("eq");
            	  if(registers.get(args[0]) == registers.get(args[1])) {
            		  
            		  
            		  registers.put(args[2],1);
            	  }
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
            	  System.out.println("jal");
            	  registers.put("$ra", ip);
            	  ip = Integer.parseInt(arg);
            	  break;
            	  
              case AVMParser.TRANSFER:
            	  System.out.println("transf");
            	  stack[hp] += stack[registers.get(arg)];
            	  stack[registers.get(arg)] = 0;
            	  
            	  break;
              case AVMParser.JUMPREG:
            	  System.out.println("jr");
            	  ip = registers.get(arg);
            	  break;
              case AVMParser.PRINT :
            	  System.out.println("print");
            	  System.out.println("print:"+((registers.get("$sp")<MEMSIZE) ? registers.get("$a0"):"Empty stack!"));
                break;
              case AVMParser.HALT :
            	  System.out.println("halt");
	             //to print the result 
	              System.out.println("\nResult: " + stack[hp] + "\n");
	              return;
             	
             	
            }
            
            
            bytecode.toPrint();
            System.out.println("-------------------------\n");
    	} 
      }
    } 
    
    
}