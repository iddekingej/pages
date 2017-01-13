package org.elaya.page.element;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.JSWriter;
import org.elaya.page.Writer;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.data.Data;
import org.xml.sax.SAXException;

public class LinkMenuItem extends BaseMenuItem<ElementThemeItem> {
	private String url;
	private String text;
	private String iconUrl;
	
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
	
	public String getIconUrl()
	{
		return iconUrl;
	}
	
	public void setIconUrl(String piconUrl)
	{
		iconUrl=piconUrl;
	}
	
	public String getText()
	{
		return text;
	}
	
	@Override
	protected void makeSetupJs(JSWriter writer,Data pdata)  throws ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, ReplaceVarException 
	{
		Data data=getData(pdata);
		writer.objVar("text", writer.replaceVariables(data,text));
		writer.objVar("url", writer.processUrl(data,url));
		writer.objVar("iconUrl",writer.processUrl(data,iconUrl));
	}
	

	@Override
	public void displayElement(int id,Writer pstream, Data pdata) {
//Empty because display is handled by js!
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
