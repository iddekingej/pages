package org.elaya.page.quickform;

import java.io.IOException;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Element;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.PageElement;
import org.elaya.page.SubmitType;
import org.elaya.page.Writer;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.data.Data;
import org.xml.sax.SAXException;


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
	
	@Override
	public void addJsFile(Set<String> pset)
	{
		pset.add("form.js");
	}
	
	@Override
	public boolean checkElement(Element<?> pelement) 
	{
		return pelement instanceof FormElement; 
	}
	
	public void setTitle(String ptitle)
	{
		title=ptitle;
	}
	
	@Override
	protected String getJsClassName()
	{
		return "TForm";
	}
	
	@Override
	public String getThemeName()
	{	
		return "FormThemeItem";		
	}
	
	@Override
	public void display(Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException  
	{
		try{
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
			themeItem.formFooterOk(pwriter, getDomId(), getSubmitText());
			themeItem.formFooterBetween(pwriter);
			if(cancelUrl.length()>0){
				themeItem.formFooterCancel(pwriter, getDomId(), getCancelText());
			}
			themeItem.formFooter(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
		
	}
	
	@Override
	protected void makeSetupJs(Writer writer,Data pdata) throws ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, org.elaya.page.Element.ReplaceVarException 
	{
		String next=writer.procesUrl(replaceVariables(pdata,nextUrl));
		writer.objVar("cmd",replaceVariables(pdata,cmd));
		writer.objVar("nextUrl", next);
				
		if(this.cancelUrl.length()>0){
			writer.objVar("cancelUrl",replaceVariables(pdata,cancelUrl));			
		}
		writer.objVar("submitType",submitType.getValue());
	}
	
	@Override
	protected void preSubJs(Writer pwriter,Data pdata) throws IOException
	{
		if(this.hiddenElements!= null){
			for(String name:this.hiddenElements){
				pwriter.print("new THiddenElement(element,"+pwriter.js_toString(name)+","+pwriter.js_toString(name)+","+pwriter.js_toString(getDomId()+"_h_"+name)+").setup();\n");
			}
		}
	}
	@Override
	protected void postSubJs(Writer pwriter,Data pdata) throws IOException
	{
		pwriter.print("element.afterSetup();");
		
	}
 
}
