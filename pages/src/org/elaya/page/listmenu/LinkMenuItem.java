package org.elaya.page.listmenu;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;
public class LinkMenuItem extends BuildinListMenuItem{

	private String text;
	private String url;
	private String delUrl;
	private String editUrl;
	
	public void setText(String ptext)
	{
		text=ptext;
	}
	
	public String getText()
	{
		return text;
	}
	
	
	public void setUrl(String purl){
		url=purl;
	}
	
	public String getUrl()
	{
		return url;
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
	
	
	@Override
	public String getJsClassName() {
		return "TLinkListMenuItem";
	}
	
	@Override
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.Element.DisplayException  {
		try{
			String 	resultUrl=pwriter.processUrl(data,url);
			themeItem.linkItem(pwriter,pwriter.replaceVariables(data,text),resultUrl,delUrl,editUrl,null);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}


}
