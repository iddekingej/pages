package org.elaya.page.widget;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.DocumentType;
import org.elaya.page.PageThemeItem;
import org.elaya.page.Theme;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.AliasData;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.Data;
import org.elaya.page.core.JSWriter;
import org.elaya.page.core.Writer;
import org.elaya.page.application.PageApplicationAware;
import org.xml.sax.SAXException;

public class Page extends PageElement<PageThemeItem> implements PageApplicationAware {
 
	private String url;
	private boolean toWindowSize=false;
	Application application;
	DocumentType documentType=DocumentType.DT_XHTML;
	
	public Page() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
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

	@Override
	public String getJsName(int id)
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
	public void displayElement(int id,Writer pwriter,Data pdata) throws org.elaya.page.widget.Element.DisplayException 
	{
		try{
			LinkedHashSet<String> js=new LinkedHashSet<>();
			LinkedHashSet<String> css=new LinkedHashSet<>();

			js.add("@pagejs");
			css.add("@pagecss");
			getAllCssFiles(css);
			getAllJsFiles(js);

			LinkedHashSet<String> procCss=processSetList(css,AliasData.ALIAS_CSSFILE);
			LinkedHashSet<String> procJs=processSetList(js,AliasData.ALIAS_JSFILE);

			themeItem.pageHeader(pwriter,documentType,procJs,procCss);	
		}catch(Exception e){
			throw new DisplayException(e);
		}
	}	
	
	@Override
	public void displayElementFooter(int id,Writer pwriter,Data pdata) throws org.elaya.page.widget.Element.DisplayException 
	{
		try{
			pwriter.generateJs();
			themeItem.pageFooter(pwriter);
		}catch(Exception e){
			throw new DisplayException(e);
		}

	}
	
	@Override
	protected void generateElementJs(int id,JSWriter pwriter,Data pdata)
	{
		if(toWindowSize){
			pwriter.printNl("pages.page.initToWindowSize();");
		}		
		pwriter.printNl("let element=pages.page;\nwidgetParent=element;");	
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

	@Override
	public void afterSetup() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException  
	{
		setTheme(new Theme(application.getThemeBase()));
	}



}



