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
}
