package org.elaya.page.jsplug;

import org.elaya.page.Element;
import org.elaya.page.core.JSWriter;
import org.elaya.page.data.Data;
import org.elaya.page.data.Data.KeyNotFoundException;
import org.elaya.page.data.DynamicMethod;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public String toJsString(String pvalue) throws JSONException{
		return JSONObject.valueToString(pvalue);
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
	
	public void display(JSWriter writer,Data data) throws JSONException, KeyNotFoundException 
	{
		
	}

}
