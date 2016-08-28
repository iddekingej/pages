package org.elaya.page;

public class PageExceptions {

	static public class InvalidElement extends Exception{

		private static final long serialVersionUID = -1560372881416540891L;
		
		public InvalidElement(Element<?> p_element,Element<?> p_where,String p_requeredClass){
			super("Wrong type of element("+p_element.getClass().getName()+"), a subelement of "+p_element.getClass().getName()+" requires a "+p_requeredClass);
		}
	}
}
