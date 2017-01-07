package org.elaya.page;
import org.w3c.dom.Node;

public class ElementVariant {
	private String name;
	private Node   content;
	
	public ElementVariant(String pname,Node pcontent) {
		name=pname;
		content=pcontent;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Node getContent()
	{
		return content;
	}

}
