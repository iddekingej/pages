package org.elaya.page.quickform;


public class FormExceptions {
	static public class ValueNotFoundException extends Exception{
		
		private static final long serialVersionUID = 202981015266904672L;
		private String varName;
		String getVarName(){ return varName;}
		
		ValueNotFoundException(String p_varName){
			super("Value not found for element"+p_varName);
			varName=p_varName;
		}
	}
	
	static public class InvalidPropertyValue extends Exception{


		private static final long serialVersionUID = 2668483595983679387L;

		InvalidPropertyValue(String p_message){
			super(p_message);
		}
	}
	

	
	static public class ElementNoSub extends Exception{

		private static final long serialVersionUID = 3888842605596529222L;
		
		public ElementNoSub(String p_where){
			super("Element "+p_where+" has no subelements");
		}
	}
	
	static public class propertyNotSet extends Exception{

		private static final long serialVersionUID = -2937862517810211929L;

		public propertyNotSet(String p_property,String p_formName){
			super(p_property+" not set for form '"+p_formName+"'");
		}
	}
}
