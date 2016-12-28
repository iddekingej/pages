package org.elaya.page.jsplug;

import org.elaya.page.Element;
import org.elaya.page.JSWriter;
import org.elaya.page.data.DynamicMethod;

public abstract class JSPlug extends DynamicMethod{
	private Element<?> parent;
	
	public static class InvalidJsPlugType extends Exception
	{

		private static final long serialVersionUID = 4931294830378071369L;

		public InvalidJsPlugType(Object pobject)
		{
			super("Invalid jsplug "+(pobject==null?"null object":pobject.getClass().getName()));
		}
	}
	
	
	protected boolean checkParent(Element<?> pelement)
	{
		return true;
	}
	
	public void setParent(Element<?> pparent) throws InvalidJsPlugType
	{
		if(!checkParent(pparent)){
			throw new InvalidJsPlugType(pparent);
		}
		parent=pparent;
	}
	
	public Element<?> getParent()
	{

		return parent;
	}
	
	public void display(JSWriter writer) 
	{
		
	}
}
