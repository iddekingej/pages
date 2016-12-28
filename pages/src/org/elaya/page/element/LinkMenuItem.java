package org.elaya.page.element;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
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
	protected void makeSetupJs(Writer writer,Data pdata) throws IOException, org.elaya.page.Element.ReplaceVarException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed 
	{
		Data data=getData(pdata);
		writer.objVar("text", replaceVariables(data,text));
		writer.objVar("url", writer.procesUrl(replaceVariables(data,url)));
		writer.objVar("iconUrl",writer.procesUrl(replaceVariables(data,iconUrl)));
	}
	

	@Override
	public void display(Writer pstream, Data pdata) {

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
