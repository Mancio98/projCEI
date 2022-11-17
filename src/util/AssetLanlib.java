package util;

import ast.*;

public class AssetLanlib {
  
  private static int labCount=0; 

  private static String funCode="";

  //valuta se il tipo "a" == "b"
  public static boolean isSubtype (Node a, Node b) {
    return a.getClass().equals(b.getClass()) ; 
  } 
  
  public static String freshLabel() { 
	return "label"+(labCount++);
  } 
  
  public static void putCode(String c) { 
    funCode+="\n"+c; 
  } 
  
  public static String getCode() { 
    return funCode;
  }


}