package org.elaya.page.form;

import org.elaya.page.Element;

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
	
	static public class InvalidElement extends Exception{

		private static final long serialVersionUID = -4997959418112427282L;
		public InvalidElement(Element p_element,Element p_where,String p_requeredClass){
			super("Wrong type of element("+p_element.getClass().getName()+"), a subelement of "+p_element.getClass().getName()+" requires a "+p_requeredClass);
		}
	}
	
	static public class ElementNoSub extends Exception{

		private static final long serialVersionUID = 3888842605596529222L;
		
		public ElementNoSub(String p_where){
			super("Element "+p_where+" has no subelements");
		}
	}
}
