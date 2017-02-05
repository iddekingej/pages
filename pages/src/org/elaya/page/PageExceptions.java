package org.elaya.page;

import org.elaya.page.widget.Element;

public class PageExceptions {

	public static  class InvalidElement extends Exception{

		private static final long serialVersionUID = -1560372881416540891L;
		
		public InvalidElement(Element<?> pelement,Element<?> pwhere,String prequeredClass){
			super("Wrong type of element("+pelement.getClass().getName()+"), a subelement of "+pelement.getClass().getName()+" requires a "+prequeredClass);
		}
	}
	private PageExceptions(){}
}
