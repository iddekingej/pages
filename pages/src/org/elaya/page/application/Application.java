package org.elaya.page.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.AliasData;
import org.elaya.page.AliasParser;
import org.elaya.page.Errors;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.NormalizeClassNameException;
import org.elaya.page.Page;
import org.elaya.page.UiXmlParser;
import org.elaya.page.data.Url;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.xml.sax.SAXException;

public abstract class Application{

	private String themeBase="org.elaya.page.defaultTheme";	
	private String aliasFiles;
	private HashMap<String,Page> pageCache=new HashMap<>(); 
	private HashMap<String,AliasData> aliasses;
	private String xmlPath="../pages/"; 
	private String defaultDBConnection;
	private String classBase="";
	 
	
	public class DefaultDBConnectionNotSet extends Exception
	{
		private static final long serialVersionUID = 7128805697742441199L;
		public DefaultDBConnectionNotSet()
		{
			super("Default DB connection(defaultDBConnection) not set application");
		}
	}
	
	class InvalidAliasType extends Exception
	{

		private static final long serialVersionUID = -7739276897593055425L;

		public InvalidAliasType(String ptypeReq,String ptypeGot)
		{
			super("Invalid alias type, '"+ptypeReq+"' expected but '"+ptypeGot+"' found");
		}
	}
	/**
	 * When loading a xml file, "classBase" is added to the classname when classname is relative
	 * ("start with a ".")
	 *  
	 * @param pclassBase
	 */
	public void setClassBase(String pclassBase)
	{
		classBase=pclassBase;
	}
	
	public String getClassBase()
	{
		return classBase;
	}
	public void setDefaultDBConnection(String pdefaultDBConnection)
	{
		defaultDBConnection=pdefaultDBConnection;
	}
	
	public String getDefaultDBConnection()
	{
		return defaultDBConnection;
	}
	
	public void setXmlPath(String ppath)
	{
		xmlPath=ppath;
	}
	
	public String getXmlPath()
	{
		return xmlPath;
	}
	//--(xml parsing )-------------------
	
	public String normalizeClassName(String name,String ptype) throws NormalizeClassNameException 
	{
		if(name.charAt(0)=='.'){
			return classBase+name;
		} else if(name.charAt(0)=='@'){
			try{
				return getAlias(name.substring(1), ptype);
			}catch(LoadingAliasFailed|InvalidAliasType   e){
				throw new Errors.NormalizeClassNameException("Normalizing class name "+name+"failed",e);
			}
		} 
		return name;
	}
	
	//--(db)------------------------------------------------------------------------
	
	public abstract DriverManagerDataSource getDB(String pname);
	
	public DriverManagerDataSource getDefaultDB() throws DefaultDBConnectionNotSet 
	{
		if(defaultDBConnection == null ||defaultDBConnection==""){
			throw new DefaultDBConnectionNotSet();
		}
		return getDB(defaultDBConnection);
	}
	
/**
 * Called after parser is created. This routine can be used to set for example initializers
 * 
 * @param pparser
 */
	protected void initUiParser(UiXmlParser pparser)
	{
		
	}
	
/**
 * Parse UI definition file in pfileName and returns the Page Object
 * The XML ui definition must describe a page.
 * When pcache=true the page object is cached. When this function is called
 * again with the same filename and pcache=true than a cached copy is returned.
 * 
 * @param pfileName  XML describing the page
 * @param pcache     Cache Page object 
 * @return            Page object representing page
 * @throws Exception  
 */
	
	
	
	public synchronized Page loadPage(String pfileName,boolean pcache) throws Exception
	{
		if(pcache && pageCache.containsKey(pfileName)){
			return pageCache.get(pfileName);
		}
		UiXmlParser parser=new UiXmlParser(this,getClass().getClassLoader());
		initUiParser(parser);
		Object object=parser.parse(pfileName);

		
		List<String> errors=parser.getErrors();
		if(!errors.isEmpty()){
			StringBuilder text=new StringBuilder();
			for(String error:errors){
				text.append(pfileName+":"+error+"\n");
			}
			throw new Errors.LoadingPageFailed(text.toString());
		}
		Page page;
		if(object instanceof Page){
			page=(Page)object;	
		}   else {
			throw new Errors.LoadingPageFailed("File "+pfileName+" contains not a Page but a '"+object.getClass().getName()+"'");
		}
		if(pcache){
			pageCache.put(pfileName, page);
		}
		return page;
	}
	
	public String getRealConfigPath(String pfileName)
	{
		String fileName=pfileName;
		if(fileName.charAt(0)!='/'){
			fileName=xmlPath+fileName;
		} 
		return fileName;
	}
	
	public InputStream getConfigStream(String pfileName) throws FileNotFoundException
	{
		String fileName=getRealConfigPath(pfileName);
		InputStream stream=getClass().getResourceAsStream(fileName);
		if(stream==null){
			throw new FileNotFoundException(pfileName+"("+fileName+")");
		}
		return stream;
	}
	
/**
 * Load aliases in fileName and add them to global alias list
 * 
 * @param fileName add aliases in xml file
 * @throws LoadingAliasFailed 
 */
	private void addAliases(String fileName) throws LoadingAliasFailed    
	{
		AliasParser parser=new AliasParser();
		try{
			InputStream input=getConfigStream(fileName);
			parser.parseAliases(input, aliasses);
		} catch(ParserConfigurationException|SAXException|IOException e){
			throw new Errors.LoadingAliasFailed("Loading alias file "+fileName+" failed",e);
		}
		
		if(!parser.getErrors().isEmpty()){
			StringBuilder text=new StringBuilder();
			for(String error:parser.getErrors()){
				text.append("\n").append(error);
			}
			throw new Errors.LoadingAliasFailed("Loading alias file "+fileName+" failed, error:"+text);
		}
	}
	
	public void setAliasFiles(String paliasFiles) 
	{
		aliasFiles=paliasFiles;
	}
	
	private void loadAliasFiles() throws LoadingAliasFailed 
	{
		aliasses=new HashMap<>();
		String[] fileNames=aliasFiles.split(",");
		for(String fileName:fileNames){
			addAliases(fileName);
		}
	}
	
	public Map<String,AliasData> getAliases() throws LoadingAliasFailed  
	{
		if(aliasses==null){
			loadAliasFiles();
		}
		return aliasses;
	}
	 
	public String getAlias(String pname,String ptype) throws LoadingAliasFailed, InvalidAliasType  
	{
		AliasData data=getAliases().get(pname);
		if(data != null){			
			if(data.getType() != ptype){
				throw new InvalidAliasType(ptype,data.getType());
			}
			return data.getValue();
		}
		return null;
	}
	
	public String getAlias(String pname,String ptype,boolean pmandatory) throws ParserConfigurationException, SAXException, IOException, InvalidAliasType, Errors.AliasNotFound, LoadingAliasFailed 
	{
		String returnValue=getAlias(pname,ptype);
		if((returnValue==null) && pmandatory){
			throw new Errors.AliasNotFound(pname);
		}
		return returnValue;
	}

	public Url getUrlByAlias(String pname) throws ParserConfigurationException, SAXException, IOException,  InvalidAliasType, Errors.AliasNotFound, LoadingAliasFailed 
	{
		return new Url(getAlias(pname,AliasData.ALIAS_URL,true));
	}
	
	public String getThemeBase(){
		return themeBase;
	}
	
	public void setThemeBase(String pbase)
	{
		themeBase=pbase;
	}

}
