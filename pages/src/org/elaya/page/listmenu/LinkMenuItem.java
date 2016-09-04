package org.elaya.page.listmenu;
import org.elaya.page.LinkType;
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
	public void display(Object p_value) throws Exception {
		String l_url="";
		if(linkType.equals(LinkType.LINK_ABSOLUTE)||linkType.equals(LinkType.LINK_RELATIVE)){
			l_url=url;
		} else if(linkType.equals(LinkType.LINK_APPLICATION)){
			l_url=themeItem.getApplication().getBasePath()+l_url;
		}
		themeItem.linkItem(getDomId(),text, url);
	}


}
