package org.elaya.page.quickform;


public class FormExceptions {
	public static class ValueNotFoundException extends Exception{
		
		private static final long serialVersionUID = 202981015266904672L;
		private final String varName;
		
		ValueNotFoundException(String pvarName){
			super("Value not found for element"+pvarName);
			varName=pvarName;
		}
		
		String getVarName(){ return varName;}

	}
	
	public static class InvalidPropertyValue extends Exception{


		private static final long serialVersionUID = 2668483595983679387L;

		InvalidPropertyValue(String pmessage){
			super(pmessage);
		}
	}
	

	
	public static class ElementNoSub extends Exception{

		private static final long serialVersionUID = 3888842605596529222L;
		
		public ElementNoSub(String pwhere){
			super("Element "+pwhere+" has no subelements");
		}
	}
	
	public static class PropertyNotSet extends Exception{

		private static final long serialVersionUID = -2937862517810211929L;

		public PropertyNotSet(String property,String formName){
			super(property+" not set for form '"+formName+"'");
		}
	}
	
	private FormExceptions(){}
}
