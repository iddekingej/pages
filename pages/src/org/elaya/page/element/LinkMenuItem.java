package org.elaya.page.element;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class LinkMenuItem extends BaseMenuItem<ElementThemeItem> {
	private String url;
	private String text;
	
	public void setUrl(String p_url)
	{
		url=p_url;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setText(String p_text)
	{
		text=p_text;
	}
	
	public String getText()
	{
		return text;
	}
	
	protected void makeSetupJs(Writer p_writer,Data p_data) throws Exception
	{
		p_writer.objVar("text", text);
		p_writer.objVar("url", url);
	}
	
	public LinkMenuItem() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void display(Writer p_stream, Data p_data) throws Exception {
		// TODO Auto-generated method stub

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
