package org.elaya.page;

public class FormExceptions {
	static class ValueNotFoundException extends Exception{
		private String varName;
		private static final long serialVersionUID = 1L;
		String getVarName(){ return varName;}
		
		ValueNotFoundException(String p_varName){
			super("Value not found for element"+p_varName);
			varName=p_varName;
		}
	}
}
