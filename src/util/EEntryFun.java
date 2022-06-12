package util;

import ast.dec.FunNode;
import ast.type.Type;
import util.EEnvironment.EffectState;

//Used for the insertion of an ID in the HashMap
public class EEntryFun extends EEntry {
	
	private EEnvironment env0;
	private EEnvironment env1;
	private FunNode fun;
	
	public EEntryFun(int nl)
	{
		super(nl);
	} 
	  
	public EEntryFun(int nl, EEnvironment env0, EEnvironment env1)
	{
		super(nl);
		this.env0 = env0;
		this.env1 = env1;
		this.fun = null;
	}
	
	public EEntryFun(int nl, EEnvironment env0, EEnvironment env1, FunNode fun)
	{
		super(nl);
		this.env0 = env0;
		this.env1 = env1;
		this.fun = fun;
	}
	
	public EEnvironment getEnv0() {
		return this.env0;
	}
	
	public EEnvironment setEnv0(EEnvironment env0) {
		return this.env0 = env0;
	}
	
	public EEnvironment getEnv1() {
		return this.env1;
	}
	
	public EEnvironment setEnv1(EEnvironment env1) {
		return this.env1 = env1;
	}
	
	public FunNode setFunNode(FunNode fun) {
		return this.fun = fun;
	}
	
	public FunNode getFunNode() {
		return this.fun;
	}
}