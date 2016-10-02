package org.elaya.page.quickform;

import java.io.IOException;
import java.util.Set;
import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.SubmitType;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class Form extends PageElement<FormThemeItem>{
	
	public Form() {
		super();
		setIsNamespace(true);
	}

	private String title;
	private String url="";
	private String cmd;
	private String width="300px";
	private String submitText="save";
	private String cancelText="cancel";
	private String nextUrl="";
	private String cancelUrl="";
	private String[] hiddenElements=null;
	private SubmitType submitType=SubmitType.json;
	
	public void setHiddenElements(String p_hiddenElements)
	{
		hiddenElements=p_hiddenElements.split(",");
	}
	
	public String[] getHiddenElements()
	{
		return hiddenElements;
	}
	
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
	
	public void setCancelText(String p_text)
	{
		cancelText=p_text;
	}
	
	public String getCancelText()
	{
		return cancelText;
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
		if(hiddenElements!=null){
			for(String l_name:hiddenElements){
				themeItem.formHiddenElement(p_writer, getDomId()+"_h_"+l_name, l_name, l_data.getString(l_name));
			}
		}
		displaySubElements(p_writer,l_data);
		themeItem.formFooterBegin(p_writer);
		themeItem.FormFooterOk(p_writer, getDomId(), getSubmitText());
		themeItem.FormFooterBetween(p_writer);
		if(cancelUrl.length()>0)themeItem.FormFooterCancel(p_writer, getDomId(), getCancelText());
		themeItem.formFooter(p_writer);
		
	}
	
	protected void makeSetupJs(Writer p_writer,Data p_data) throws Exception
	{
		p_writer.print("this.cmd="+p_writer.js_toString(replaceVariables(p_data,cmd))+";\n");
		p_writer.print("this.nextUrl="+p_writer.js_toString(replaceVariables(p_data,nextUrl))+";\n");
		if(this.cancelUrl.length()>0)p_writer.print("this.cancelUrl="+p_writer.js_toString(replaceVariables(p_data,cancelUrl))+";\n");
		p_writer.print("this.submitType="+p_writer.js_toString(submitType.getValue()));
	}
	
	@Override
	protected void preSubJs(Writer p_writer,Data p_data) throws IOException
	{
		if(this.hiddenElements!= null){
			Element<?> l_namespaceParent=getNamespaceParent();
			if(getIsNamespace()){
				l_namespaceParent=this;
			}
			for(String l_name:this.hiddenElements){
				p_writer.print("var l_element=new THiddenElement("+getJsFullname()+","+p_writer.js_toString(l_name)+","+p_writer.js_toString(l_name)+","+p_writer.js_toString(getDomId()+"_h_"+l_name)+");\n");
				if(l_namespaceParent!=null){
					p_writer.print("l_element.namespaceParent="+l_namespaceParent.getNamespaceName()+";\n");
				}
				p_writer.print("l_element.setup();");
			}
		}
	}
	@Override
	protected void postSubJs(Writer p_writer,Data p_data) throws IOException
	{
		p_writer.print(getJsFullname()+".afterSetup();");
		
	}
 
}
