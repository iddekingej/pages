package org.elaya.page.quickform;

import java.util.Set;
import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.SubmitType;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class Form extends PageElement<FormThemeItem>{
	
	public Form() {
		super();
	}

	private String title;
	private String url="";
	private String cmd;
	private String width="300px";
	private String submitText="save";
	private String nextUrl;
	private String cancelUrl;
	private SubmitType submitType=SubmitType.json;
	
	public void setNextUrl(String p_nextUrl)
	{
		nextUrl=p_nextUrl;
	}
	
	public String getNextUrl()
	{
		return nextUrl;
	}
	
	public void setCancelUrl(String p_cancelUrl)
	{
		cancelUrl=p_cancelUrl;
	}
	
	public String getCancelUrl()
	{
		return cancelUrl;
	}
	
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
	protected String getJsClassName()
	{
		return "TForm";
	}
	
	public String getThemeName()
	{	
		return "FormThemeItem";		
	}
	
	public void preElement(Writer p_writer,Element<?> p_element) throws Exception
	{
		String l_label="";
		if(p_element instanceof FormElement){
			l_label=((FormElement<?>)p_element).getLabel();
		}
		themeItem.formElementBegin(p_writer,l_label);		
	}
	
	public void postElement(Writer p_writer,Element<?> p_element) throws Exception
	{
		themeItem.formElementEnd(p_writer);
	}
	
	public void display(Writer p_writer,Data p_data) throws Exception
	{
		Data l_data=getData(p_data);		
		String l_method="";
		if(submitType.equals(SubmitType.get)){
			l_method="get";
		} else if(submitType.equals(SubmitType.post)){
			l_method="post";
		}
		themeItem.formHeader(p_writer,getDomId(),replaceVariables(l_data,title),theme.getApplication().getBasePath()+replaceVariables(l_data,url),l_method,getWidth());		
		displaySubElements(p_writer,l_data);
		themeItem.formFooter(p_writer,getDomId(),getSubmitText(),"this.form._control.save()");
	}
	
	protected void makeSetupJs(Writer p_writer,Data p_data) throws Exception
	{
		p_writer.print("this.cmd="+themeItem.js_toString(replaceVariables(p_data,cmd))+";\n");
		p_writer.print("this.nextUrl="+themeItem.js_toString(replaceVariables(p_data,nextUrl))+";\n");
		p_writer.print("this.submitType="+themeItem.js_toString(submitType.getValue()));
	}
	
 
}
