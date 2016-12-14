package org.elaya.page.application;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elaya.page.AliasData;
import org.elaya.page.AliasParser;
import org.elaya.page.Errors;
import org.elaya.page.Page;
import org.elaya.page.UiXmlParser;
import org.elaya.page.data.Url;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
	//--(xml parsing -------------------
	
	public String normalizeClassName(String pname,String ptype) throws Exception
	{
		if(pname.charAt(0)=='.'){
			return classBase+pname;
		} else if(pname.charAt(0)=='@'){
			return getAlias(pname.substring(1), ptype);
		} 
		return pname;
	}
	
	//--(db)------------------------------------------------------------------------
	
	public abstract DriverManagerDataSource getDB(String pname);
	
	public DriverManagerDataSource getDefaultDB() throws Exception
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
	
/* Alias handling */
//TODO error handling
	private void addAliasses(String pfileName) throws Exception
	{
		AliasParser parser=new AliasParser();
		InputStream input=getConfigStream(pfileName);
		parser.parseAliases(input, aliasses);
		
		if(!parser.getErrors().isEmpty()){
			StringBuilder text=new StringBuilder();
			for(String error:parser.getErrors()){
				text.append("\n").append(error);
			}
			throw new Errors.LoadingPageFailed("Error:"+text);
		}
	}
	
	public void setAliasFiles(String paliasFiles) 
	{
		aliasFiles=paliasFiles;
	}
	
	private void loadAliasFiles() throws Exception
	{
		aliasses=new HashMap<>();
		String[] fileNames=aliasFiles.split(",");
		for(String fileName:fileNames){
			addAliasses(fileName);
		}
	}
	
	public Map<String,AliasData> getAliasses() throws Exception
	{
		if(aliasses==null){
			loadAliasFiles();
		}
		return aliasses;
	}
	 
	public String getAlias(String pname,String ptype) throws Exception
	{
		AliasData data=getAliasses().get(pname);
		if(data != null){			
			if(data.getType() != ptype){
				throw new InvalidAliasType(ptype,data.getType());
			}
			return data.getValue();
		}
		return null;
	}
	
	public String getAlias(String pname,String ptype,boolean pmandatory) throws Exception
	{
		String returnValue=getAlias(pname,ptype);
		if((returnValue==null) && pmandatory){
			throw new Errors.AliasNotFound(pname);
		}
		return returnValue;
	}

	public Url getUrlByAlias(String pname) throws Exception
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
