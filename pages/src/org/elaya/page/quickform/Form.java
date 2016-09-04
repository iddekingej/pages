package org.elaya.page.quickform;

import java.util.Set;
import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.SubmitType;

public class Form extends PageElement<FormThemeItem>{
	
	public Form() {
		super();
	}

	private String title;
	private String url;
	private String cmd;
	private String width="300px";
	private String submitText="save";
	private SubmitType submitType=SubmitType.json;
	
	public void setWidth(String p_width)
	{
		width=p_width;
	}
	
	public String getWidth()
	{
		return width;
	}
	
	public void setUrl(String p_url)
	{
		url=p_url;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public void setSubmitText(String p_text)
	{
		submitText=p_text;
	}
	
	public String getSubmitText()
	{
		return submitText;
	}
	public void setSubmitType(SubmitType p_submitType)
	{
		submitType=p_submitType;
	}
	
	public SubmitType getSubmitType()
	{
		return submitType;
	}
	
	public void setCmd(String p_cmd)
	{
		cmd=p_cmd;
	}
	
	
	public String getCmd(){ return cmd;}
	
	public void addJsFile(Set<String> p_set)
	{
		p_set.add("form.js");
	}
	
	public boolean checkElement(Element<?> p_element) 
	{
		return p_element instanceof FormElement; 
	}
	
	public void setTitle(String p_title)
	{
		title=p_title;
	}
	
	public String getThemeName()
	{	
		return "FormThemeItem";		
	}
	
	public void display() throws Exception
	{
		String l_method="";
		if(submitType.equals(SubmitType.get)){
			l_method="get";
		} else if(submitType.equals(SubmitType.post)){
			l_method="post";
		}
		themeItem.formHeader(getDomId(),title,theme.getApplication().getBasePath()+url,l_method,getWidth());
		
		FormElement<?> l_item;
		Object l_value;		
		for(Element<?> l_element:elements){
			if(l_element instanceof FormElement){
				l_item=(FormElement<?>)l_element;
				themeItem.formElementBegin(l_item.getLabel());
				if(l_item.hasValue()){
					if(hasValue(l_item.getName())){
						l_value=getValue(l_item.getName());
					} else {
						l_value="";
					}
				} else {
					l_value="";
				}
				l_item.display(l_value);			
				themeItem.formElementEnd();
			}
		}
		String l_submitJs="";
		if(submitType.equals(SubmitType.json)){
			l_submitJs="this.form._control.sendData();";
		} else if(submitType.equals(SubmitType.get)||submitType.equals(SubmitType.post)){
			l_submitJs="this.form._control.submit();";
		}

		themeItem.formFooter(getDomId(),getSubmitText(),l_submitJs);
		themeItem.jsBegin();
		themeItem.print("\n{\n ");
		themeItem.print("var l_form=new TForm("+getWidgetParent().getJsFullname()+","+themeItem.js_toString(getJsName())+","+themeItem.js_toString(getName())+","+themeItem.js_toString(getDomId())+");\n");
		themeItem.print("l_form.cmd="+themeItem.js_toString(cmd)+";\n");
		for(Element<?> l_element:elements){
			if(l_element instanceof FormElement){
				FormElement<?> l_fe=(FormElement<?>)l_element;
				themeItem.print(l_fe.getObjectJs("l_form")+"\n");
			}
		}
		themeItem.print("\n}\n");
		themeItem.jsEnd();
	}
	
 
}
