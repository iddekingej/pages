package org.elaya.page.data;

public class LinkMenuData extends LinkData {
	private Object id;
	
	public Object getId()
	{
		return id;
	}
	
	public LinkMenuData(Url purl, String ptext,Object pid) {
		super(purl, ptext);
		id=pid;
	}

}
