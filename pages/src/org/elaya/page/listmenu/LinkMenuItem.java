package org.elaya.page.listmenu;
import org.elaya.page.LinkType;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;
public class LinkMenuItem extends BuildinListMenuItem{

	private String text;
	private LinkType linkType=LinkType.LINK_APPLICATION;
	private String url;
	
	public void setText(String p_text)
	{
		text=p_text;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setLinkType(LinkType p_type){
		linkType=p_type;
	}
	
	public LinkType getLinkType()
	{
		return linkType;
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
		String l_url="";
		if(linkType.equals(LinkType.LINK_ABSOLUTE)||linkType.equals(LinkType.LINK_RELATIVE)){
			l_url=url;
		} else if(linkType.equals(LinkType.LINK_APPLICATION)){
			l_url=getApplication().getBasePath()+l_url;
		}
		themeItem.linkItem(p_writer,getDomId(),replaceVariables(l_data,text), replaceVariables(l_data,url));
	}


}
