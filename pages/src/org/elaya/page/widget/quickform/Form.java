package org.elaya.page.widget.quickform;

import java.io.IOException;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.SubmitType;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.Data;
import org.elaya.page.core.JSWriter;
import org.elaya.page.core.Writer;
import org.elaya.page.core.Data.KeyNotFoundException;
import org.elaya.page.widget.Element;
import org.elaya.page.widget.PageElement;
import org.json.JSONException;
import org.xml.sax.SAXException;


public class Form extends PageElement<FormThemeItem>{
	
	private String title;
	private String url="";
	private String cmd;
	private String cmdField="cmd";
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

	public void setCmdField(String pcmdField)
	{
		cmdField=pcmdField;
	}
	
	public String getCmdField()
	{
		return cmdField;
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
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.widget.Element.DisplayException  
	{

		try{
			String domId=getDomId(id);
			String method="";
			if(submitType.equals(SubmitType.GET)){
				method="get";
			} else if(submitType.equals(SubmitType.POST)){
				method="post";
			}
			themeItem.formHeader(pwriter,domId,pwriter.replaceVariables(data,title),pwriter.processUrl(data,url),method,getWidth());
			if(hiddenElements!=null){
				for(String name:hiddenElements){
					themeItem.formHiddenElement(pwriter, domId+"_h_"+name, name, data.getString(name));
				}
			}
		}catch(Exception e){
			throw new DisplayException("",e);
		}
		
	}
	
	@Override
	public void displayElementFooter(int id,Writer pwriter,Data data) throws org.elaya.page.widget.Element.DisplayException  
	{
		try{
			String domId=getDomId(id);
			themeItem.formFooterBegin(pwriter);
			themeItem.formFooterOk(pwriter, domId, getSubmitText());
			themeItem.formFooterBetween(pwriter);
			if(cancelUrl.length()>0){
				themeItem.formFooterCancel(pwriter, domId, getCancelText());
			}
			themeItem.formFooter(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	
	@Override
	protected void makeSetupJs(JSWriter writer,Data pdata) throws ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, ReplaceVarException, JSONException 
	{
		String next=writer.processUrl(pdata,nextUrl);
		writer.objVar("cmd",writer.replaceVariables(pdata,cmd));
		writer.objVar("nextUrl", next);
		if(url != ""){
			writer.objVar("url",writer.processUrl(pdata,url));
		}
		writer.objVar("cmdField",cmdField);		
		if(this.cancelUrl.length()>0){
			writer.objVar("cancelUrl",writer.processUrl(pdata,cancelUrl));			
		}
		writer.objVar("submitType",submitType.getValue());
	}
	
	@Override
	protected void generateElementJs(int id,JSWriter writer,Data data) throws ReplaceVarException, ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, JSONException, KeyNotFoundException
	{
		super.generateElementJs(id, writer, data);
		if(this.hiddenElements!= null){
			for(String name:this.hiddenElements){
				writer.print("new THiddenElement(widgetParent,"+writer.toJsString(name)+","+writer.toJsString(name)+","+writer.toJsString(getDomId(id)+"_h_"+name)+").setup();\n");
			}
		}
	}
	@Override
	protected void generateElementFooterJs(int id,JSWriter writer,Data data)  
	{
		super.generateElementFooterJs(id, writer, data);
		writer.print("element.afterSetup();");
		
	}
 
}
