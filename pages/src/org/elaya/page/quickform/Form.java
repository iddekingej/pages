package org.elaya.page.quickform;

import java.io.IOException;
import java.util.Set;
import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.SubmitType;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class Form extends PageElement<FormThemeItem>{
	
	private String title;
	private String url="";
	private String cmd;
	private String width="300px";
	private String submitText="save";
	private String cancelText="cancel";
	private String nextUrl="";
	private String cancelUrl="";
	private String[] hiddenElements=null;
	private SubmitType submitType=SubmitType.JSON;
	
	
	public Form() {
		super();
		setIsNamespace(true);
	}


	public void setHiddenElements(String phiddenElements)
	{
		hiddenElements=phiddenElements.split(",");
	}
	
	public String[] getHiddenElements()
	{
		return hiddenElements;
	}
	
	public void setNextUrl(String pnextUrl)
	{
		nextUrl=pnextUrl;
	}
	
	public String getNextUrl()
	{
		return nextUrl;
	}
	
	public void setCancelUrl(String pcancelUrl)
	{
		cancelUrl=pcancelUrl;
	}
	
	public String getCancelUrl()
	{
		return cancelUrl;
	}
	
	
	public void setWidth(String pwidth)
	{
		width=pwidth;
	}
	
	public String getWidth()
	{
		return width;
	}
	
	public void setUrl(String purl)
	{
		url=purl;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public void setSubmitText(String ptext)
	{
		submitText=ptext;
	}
	
	public String getSubmitText()
	{
		return submitText;
	}
	
	public void setCancelText(String ptext)
	{
		cancelText=ptext;
	}
	
	public String getCancelText()
	{
		return cancelText;
	}

	public void setSubmitType(SubmitType psubmitType)
	{
		submitType=psubmitType;
	}
	
	public SubmitType getSubmitType()
	{
		return submitType;
	}
	
	public void setCmd(String pcmd)
	{
		cmd=pcmd;
	}
	
	
	public String getCmd(){ return cmd;}
	
	public void addJsFile(Set<String> pset)
	{
		pset.add("form.js");
	}
	
	public boolean checkElement(Element<?> pelement) 
	{
		return pelement instanceof FormElement; 
	}
	
	public void setTitle(String ptitle)
	{
		title=ptitle;
	}
	protected String getJsClassName()
	{
		return "TForm";
	}
	
	public String getThemeName()
	{	
		return "FormThemeItem";		
	}
	
	public void display(Writer pwriter,Data pdata) throws Exception
	{
		Data data=getData(pdata);		
		String method="";
		if(submitType.equals(SubmitType.GET)){
			method="get";
		} else if(submitType.equals(SubmitType.POST)){
			method="post";
		}
		themeItem.formHeader(pwriter,getDomId(),replaceVariables(data,title),pwriter.getBasePath()+replaceVariables(data,url),method,getWidth());
		if(hiddenElements!=null){
			for(String name:hiddenElements){
				themeItem.formHiddenElement(pwriter, getDomId()+"_h_"+name, name, data.getString(name));
			}
		}
		displaySubElements(pwriter,data);
		themeItem.formFooterBegin(pwriter);
		themeItem.FormFooterOk(pwriter, getDomId(), getSubmitText());
		themeItem.FormFooterBetween(pwriter);
		if(cancelUrl.length()>0){
			themeItem.FormFooterCancel(pwriter, getDomId(), getCancelText());
		}
		themeItem.formFooter(pwriter);
		
	}
	
	protected void makeSetupJs(Writer pwriter,Data pdata) throws Exception
	{
		String next=pwriter.procesUrl(replaceVariables(pdata,nextUrl));
		pwriter.print("this.cmd="+pwriter.js_toString(replaceVariables(pdata,cmd))+";\n");
		pwriter.print("this.nextUrl="+pwriter.js_toString(next)+";\n");
		if(this.cancelUrl.length()>0)pwriter.print("this.cancelUrl="+pwriter.js_toString(replaceVariables(pdata,cancelUrl))+";\n");
		pwriter.print("this.submitType="+pwriter.js_toString(submitType.getValue()));
	}
	
	@Override
	protected void preSubJs(Writer pwriter,Data pdata) throws IOException
	{
		if(this.hiddenElements!= null){
			Element<?> namespaceParent=getNamespaceParent();
			if(getIsNamespace()){
				namespaceParent=this;
			}
			for(String name:this.hiddenElements){
				pwriter.print("var element=new THiddenElement("+getJsFullname()+","+pwriter.js_toString(name)+","+pwriter.js_toString(name)+","+pwriter.js_toString(getDomId()+"_h_"+name)+");\n");
				if(namespaceParent!=null){
					pwriter.print("element.namespaceParent="+namespaceParent.getNamespaceName()+";\n");
				}
				pwriter.print("element.setup();");
			}
		}
	}
	@Override
	protected void postSubJs(Writer pwriter,Data pdata) throws IOException
	{
		pwriter.print(getJsFullname()+".afterSetup();");
		
	}
 
}
