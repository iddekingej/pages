package org.elaya.page.listmenu;

import org.elaya.page.data.LinkData;
import org.elaya.page.data.Url;

public class LinkMenuData extends LinkData {
	private Object id;
	private String delUrl;
	private String editUrl;
	public LinkMenuData(Url purl, String ptext,Object pid) {
		super(purl, ptext);
		id=pid;
	}
	public Object getId()
	{
		return id;
	}
	
	public void setDelUrl(String url)
	{
		delUrl=url;
	}
	
	public String getDelUrl()
	{
		return delUrl;
	}
	
	public void setEditUrl(String url)
	{
		editUrl=url;
	}
	
	public String getEditUrl()
	{
		return editUrl;
	}
	

}
