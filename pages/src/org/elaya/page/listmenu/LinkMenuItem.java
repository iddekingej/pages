package org.elaya.page.listmenu;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;
public class LinkMenuItem extends BuildinListMenuItem{

	private String text;
	private String url;
	
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

	@Override
	public void display(Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException  {
		try{
			Data data=getData(pdata);
			String 	resultUrl=pwriter.procesUrl(replaceVariables(data,url));
			themeItem.linkItem(pwriter,getDomId(),replaceVariables(data,text),resultUrl  );
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}


}
