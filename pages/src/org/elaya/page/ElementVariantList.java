package org.elaya.page;

import java.util.HashMap;
import org.w3c.dom.Node;

public class ElementVariantList {
	public static class DuplicateElementVariant extends Exception{
		private static final long serialVersionUID = 8055504686783437244L;
		
		public DuplicateElementVariant(String name)
		{
			super("Duplicate variant '"+name+"'");
		}
	}
	
	public static class ElementVariantNotFound extends Exception{
		private static final long serialVersionUID = -6305126795362824886L;
		
		public ElementVariantNotFound(String name)
		{
			super("Element variant not found '"+name+"'");
		}
	}
	
	private HashMap<String,ElementVariant> variants=new HashMap<>();
	
	public void addElementVariant(String name,Node node) throws DuplicateElementVariant
	{
		if(variants.containsKey(name)){
			throw new DuplicateElementVariant(name);
		}
		variants.put(name,new ElementVariant(name,node));
	}
	
	public ElementVariant getElementVariantByName(String name) throws ElementVariantNotFound
	{
		if(!variants.containsKey(name)){
			return null; 
		}
		return variants.get(name);
	}
	

}
