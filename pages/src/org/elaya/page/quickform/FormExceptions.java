package org.elaya.page.quickform;


public class FormExceptions {
	static public class ValueNotFoundException extends Exception{
		
		private static final long serialVersionUID = 202981015266904672L;
		private String varName;
		String getVarName(){ return varName;}
		
		ValueNotFoundException(String pvarName){
			super("Value not found for element"+pvarName);
			varName=pvarName;
		}
	}
	
	static public class InvalidPropertyValue extends Exception{


		private static final long serialVersionUID = 2668483595983679387L;

		InvalidPropertyValue(String pmessage){
			super(pmessage);
		}
	}
	

	
	static public class ElementNoSub extends Exception{

		private static final long serialVersionUID = 3888842605596529222L;
		
		public ElementNoSub(String pwhere){
			super("Element "+pwhere+" has no subelements");
		}
	}
	
	static public class propertyNotSet extends Exception{

		private static final long serialVersionUID = -2937862517810211929L;

		public propertyNotSet(String pproperty,String pformName){
			super(pproperty+" not set for form '"+pformName+"'");
		}
	}
}
