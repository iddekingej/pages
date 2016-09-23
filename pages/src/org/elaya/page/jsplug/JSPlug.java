package org.elaya.page.jsplug;

import java.io.IOException;

import org.elaya.page.Element;
import org.elaya.page.Writer;
import org.elaya.page.data.DynamicMethod;

public abstract class JSPlug extends DynamicMethod{
	private Element<?> parent;
	
	public static class InvalidJsPlugType extends Exception
	{

		private static final long serialVersionUID = 4931294830378071369L;

		public InvalidJsPlugType(Object p_object)
		{
			super("Invalid jsplug "+(p_object==null?"null object":p_object.getClass().getName()));
		}
	}
	
	
	protected boolean checkParent(Element<?> p_element)
	{
		return true;
	}
	
	public void setParent(Element<?> p_parent) throws InvalidJsPlugType
	{
		if(!checkParent(p_parent)){
			throw new InvalidJsPlugType(p_parent);
		}
		parent=p_parent;
	}
	
	public Element<?> getParent()
	{

		return parent;
	}
	
	public void display(Writer p_writer) throws IOException
	{
		
	}
}
