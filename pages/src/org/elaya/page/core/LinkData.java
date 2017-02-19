package org.elaya.page.core;

public class LinkData {
	private Url url;
	private String text;
	public LinkData(Url purl,String ptext) {
		url=purl;
		text=ptext;
	}
	
	public String getUrlText(){ return url.getUrlText();}
	public String getText(){ return text;}

}
