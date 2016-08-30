package org.elaya.page.data;

public class LinkData {
	private Url url;
	private String text;
	public LinkData(Url p_url,String p_text) {
		url=p_url;
		text=p_text;
	}
	
	public String getUrlText(){ return url.getUrlText();}
	public String getText(){ return text;}

}
