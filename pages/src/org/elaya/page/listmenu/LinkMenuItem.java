package org.elaya.page.listmenu;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;
public class LinkMenuItem extends BuildinListMenuItem{

	private String text;
	private String url;
	
	public void setText(String p_text)
	{
		text=p_text;
	}
	
	public String getText()
	{
		return text;
	}
	
	
	public void setUrl(String p_url){
		url=p_url;
	}
	
	public String getUrl()
	{
		return url;
	}

	public LinkMenuItem() {
		super();
	}

	@Override
	public void display(Writer p_writer,Data p_data) throws Exception {
		Data l_data=getData(p_data);
		String 	l_url=getApplication().procesUrl(replaceVariables(l_data,url));
		themeItem.linkItem(p_writer,getDomId(),replaceVariables(l_data,text),l_url  );
	}


}
