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
	
	public void addVariant(ElementVariant variant) throws DuplicateElementVariant
	{
		if(variants.containsKey(variant.getName())){
			throw new DuplicateElementVariant(variant.getName());
		}
		variants.put(variant.getName(),variant);
	}
	
	public ElementVariant getElementVariantByName(String name)
	{
		if(!variants.containsKey(name)){
			return null; 
		}
		return variants.get(name);
	}
	

}
