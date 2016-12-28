package org.elaya.page;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.data.Data;
import org.xml.sax.SAXException;

public class Page extends PageElement<PageThemeItem> implements PageApplicationAware {
 
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

	
	@Override
	public Application getApplication() {
		return application;
	}
	
	@Override	
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

	private LinkedHashSet<String> processSetList(LinkedHashSet<String> pin,String ptype) throws ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed 
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
	public void display(Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException 
	{
		try{
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
			pwriter.print("var widgetParent;\nvar namespaceParent;\n");
			generateJs(pwriter,pdata);
			pwriter.jsEnd();


			themeItem.pageFooter(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}	
	
	@Override
	protected void makeJsObject(Writer pwriter,Data pdata)
	{
		/* Page doesn't need to create a js object here, already done in page.js */
	}

	@Override
	public void preSubJs(Writer pwriter,Data pdata) throws IOException 
	{
		if(toWindowSize){
			pwriter.print("pages.page.initToWindowSize();\n");
		}		
		pwriter.print("let element=pages.page;\n");
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

	public void initTheme() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException  
	{
		setTheme(new Theme(application.getThemeBase()));
	}


}



