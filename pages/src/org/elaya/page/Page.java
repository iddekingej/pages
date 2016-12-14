package org.elaya.page;

import java.util.LinkedHashSet;

import org.elaya.page.application.Application;
import org.elaya.page.data.Data;

public class Page extends PageElement<PageThemeItem> {
 
	private String url;
	private int idCnt=0;
	private boolean toWindowSize=false;
	Application application;
	DocumentType documentType=DocumentType.DT_XHTML;
	
	public Page()
	{		
		setIsNamespace(true);
	}

	public void setDocumentType(DocumentType ptype)
	{
		documentType=ptype;
	}
	
	public DocumentType getDocumentType()
	{
		return documentType;
	}
	
	public void setApplication(Application papplication)
	{
		application=papplication;
	}
	
	public String getUrl(){ return url;}
	
	public void setUrl(String purl)
	{		
		url=purl;
	}
	
	public void setToWindowSize(Boolean pflag)
	{
		toWindowSize=pflag;
	}
	
	public boolean getToWindowSize()
	{
		return toWindowSize;
	}
	
	public int newId()
	{
		idCnt++;
		return idCnt;
	}

	@Override
	public String getJsName()
	{
		return "pages.page";
	}

	private LinkedHashSet<String> processSetList(LinkedHashSet<String> pin,String ptype) throws Exception
	{
		LinkedHashSet<String> returnValue=new LinkedHashSet<>();
		for(String value:pin){
			if(value.charAt(0)=='@'){
				value=application.getAlias(value.substring(1),ptype,true);
				String[] list=value.split(",");
				for(String part:list){
					returnValue.add(part);
				}
			} else {
				returnValue.add(value);
			}
		}
		return returnValue;
	}
	
	@Override
	public void display(Writer pwriter,Data pdata) throws Exception
	{
		Data data=getData(pdata);
		LinkedHashSet<String> js=new LinkedHashSet<>();
		LinkedHashSet<String> css=new LinkedHashSet<>();
		
		js.add("@pagejs");
		css.add("@pagecss");
		getAllCssFiles(css);
		getAllJsFiles(js);
		
		LinkedHashSet<String> procCss=processSetList(css,AliasData.ALIAS_CSSFILE);
		LinkedHashSet<String> procJs=processSetList(js,AliasData.ALIAS_JSFILE);
		
		themeItem.pageHeader(pwriter,documentType,procJs,procCss);	
		displaySubElements(pwriter,data);
		pwriter.jsBegin();
		generateJs(pwriter,pdata);
		pwriter.jsEnd();
		

		themeItem.pageFooter(pwriter);
	}	
	
	@Override
	protected void makeJsObject(Writer pwriter,Data pdata)
	{
		/* Page doesn't need to create a js object here, already done in page.js */
	}

	@Override
	public void preSubJs(Writer pwriter,Data pdata) throws Exception
	{
		if(toWindowSize){
			pwriter.print("pages.page.initToWindowSize();");
		}
	}
	@Override
	public String getThemeName()
	{
			return "PageThemeItem";
	}
	
	@Override
	public boolean checkElement(Element<?> pelement){
		return pelement instanceof PageElement;
	}

	public void initTheme() throws Exception
	{
		setTheme(new Theme(application.getThemeBase()));
	}
	

}



