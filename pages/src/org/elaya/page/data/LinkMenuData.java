package org.elaya.page.data;

public class LinkMenuData extends LinkData {
	private Object id;
	
	public Object getId()
	{
		return id;
	}
	
	public LinkMenuData(Url p_url, String p_text,Object p_id) {
		super(p_url, p_text);
		id=p_id;
	}

}
