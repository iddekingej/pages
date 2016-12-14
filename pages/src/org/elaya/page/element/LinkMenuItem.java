package org.elaya.page.element;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class LinkMenuItem extends BaseMenuItem<ElementThemeItem> {
	private String url;
	private String text;
	
	public LinkMenuItem() {
		super();
	}

	public void setUrl(String purl)
	{
		url=purl;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setText(String ptext)
	{
		text=ptext;
	}
	
	public String getText()
	{
		return text;
	}
	
	@Override
	protected void makeSetupJs(Writer pwriter,Data pdata) throws Exception
	{
		Data data=getData(pdata);
		pwriter.objVar("text", replaceVariables(data,text));
		pwriter.objVar("url", pwriter.procesUrl(replaceVariables(data,url)));
	}
	

	@Override
	public void display(Writer pstream, Data pdata) throws Exception {

	}

	@Override
	public String getJsClassName() {
		return "TLinkMenuItem";
	}		
	
	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}
}
