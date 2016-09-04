package org.elaya.page;

public class Errors {

	public static class PropertyNotSet extends Exception{

		private static final long serialVersionUID = -378654124608489073L;

		public PropertyNotSet(String p_name)
		{
			super("Property '"+p_name+"' is mandatory, but not set");
		}
	}
	public static class ValueNotFound extends Exception{

		private static final long serialVersionUID = -1297503130626285932L;

		public ValueNotFound(String p_name){
			super("Value '"+p_name+"' not found");
		}
	}
	
	public static class duplicateElementOnPage extends Exception
	{
		private static final long serialVersionUID = 5670782478614650834L;
		
		public duplicateElementOnPage(String p_name)
		{
			super("Duplicate element name on page:"+p_name);
		}
		
	}
	
	static public class InvalidElement extends Exception{

		private static final long serialVersionUID = -4997959418112427282L;
		public InvalidElement(Element<?> p_element,Element<?> p_where){
			super("Wrong type of element("+p_element.getClass().getName()+"), a subelement of "+p_element.getClass().getName());
		}
	}
}
