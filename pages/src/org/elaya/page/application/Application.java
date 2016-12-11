package org.elaya.page.application;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.elaya.page.AliasData;
import org.elaya.page.AliasParser;
import org.elaya.page.Errors;
import org.elaya.page.Page;
import org.elaya.page.UiXmlParser;
import org.elaya.page.data.Url;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

abstract public class Application{

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

		public InvalidAliasType(String p_typeReq,String p_typeGot)
		{
			super("Invalid alias type, '"+p_typeReq+"' expected but '"+p_typeGot+"' found");
		}
	}
	/**
	 * When loading a xml file, "classBase" is added to the classname when classname is relative
	 * ("start with a ".")
	 *  
	 * @param p_classBase
	 */
	public void setClassBase(String p_classBase)
	{
		classBase=p_classBase;
	}
	
	public String getClassBase()
	{
		return classBase;
	}
	public void setDefaultDBConnection(String p_defaultDBConnection)
	{
		defaultDBConnection=p_defaultDBConnection;
	}
	
	public String getDefaultDBConnection()
	{
		return defaultDBConnection;
	}
	
	public void setXmlPath(String p_path)
	{
		xmlPath=p_path;
	}
	
	public String getXmlPath()
	{
		return xmlPath;
	}
	//--(xml parsing -------------------
	
	public String normalizeClassName(String p_name,String p_type) throws Exception
	{
		if(p_name.charAt(0)=='.'){
			return classBase+p_name;
		} else if(p_name.charAt(0)=='@'){
			return getAlias(p_name.substring(1), p_type);
		} 
		return p_name;
	}
	
	//--(db)------------------------------------------------------------------------
	
	public abstract DriverManagerDataSource getDB(String p_name);
	
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
 * @param p_parser
 */
	protected void initUiParser(UiXmlParser p_parser)
	{
		
	}
	
/**
 * Parse UI definition file in p_fileName and returns the Page Object
 * The XML ui definition must describe a page.
 * When p_cache=true the page object is cached. When this function is called
 * again with the same filename and p_cache=true than a cached copy is returned.
 * 
 * @param p_fileName  XML describing the page
 * @param p_cache     Cache Page object 
 * @return            Page object representing page
 * @throws Exception  
 */
	
	
	
	public synchronized Page loadPage(String p_fileName,boolean p_cache) throws Exception
	{
		if(p_cache && pageCache.containsKey(p_fileName)){
			return pageCache.get(p_fileName);
		}
		UiXmlParser l_parser=new UiXmlParser(this,getClass().getClassLoader());
		initUiParser(l_parser);
		Object l_object=l_parser.parse(p_fileName);

		
		List<String> l_errors=l_parser.getErrors();
		if(l_errors.size()>0){
			String l_text="";
			for(String l_error:l_errors){
				l_text =l_text +(p_fileName+":"+l_error)+"\n";
			}
			throw new Errors.LoadingPageFailed(l_text);
		}
		Page l_page;
		if(l_object instanceof Page){
			l_page=(Page)l_object;	
		}   else {
			throw new Errors.LoadingPageFailed("File "+p_fileName+" contains not a Page but a '"+l_object.getClass().getName()+"'");
		}
		if(p_cache){
			pageCache.put(p_fileName, l_page);
		}
		return l_page;
	}
	
	public String getRealConfigPath(String p_fileName)
	{
		String l_fileName=p_fileName;
		if(l_fileName.charAt(0)!='/'){
			l_fileName=xmlPath+l_fileName;
		} 
		return l_fileName;
	}
	
	public InputStream getConfigStream(String p_fileName) throws FileNotFoundException
	{
		String l_fileName=getRealConfigPath(p_fileName);
		InputStream l_stream=getClass().getResourceAsStream(l_fileName);
		if(l_stream==null){
			throw new FileNotFoundException(p_fileName+"("+l_fileName+")");
		}
		return l_stream;
	}
	
/* Alias handling */
//TODO error handling
	private void addAliasses(String p_fileName) throws Exception
	{
		AliasParser l_parser=new AliasParser();
		InputStream l_input=getConfigStream(p_fileName);
		l_parser.parseAliases(l_input, aliasses);
		
		if(!l_parser.getErrors().isEmpty()){
			String l_text="";
			for(String l_error:l_parser.getErrors()){
				l_text=l_text+"\n"+l_error;
			}
			throw new Errors.LoadingPageFailed("Error:"+l_text);
		}
	}
	
	public void setAliasFiles(String p_aliasFiles) 
	{
		aliasFiles=p_aliasFiles;
	}
	
	private void loadAliasFiles() throws Exception
	{
		aliasses=new HashMap<String,AliasData>();
		String[] l_fileNames=aliasFiles.split(",");
		for(String l_fileName:l_fileNames){
			addAliasses(l_fileName);
		}
	}
	
	public HashMap<String,AliasData> getAliasses() throws Exception
	{
		if(aliasses==null){
			loadAliasFiles();
		}
		return aliasses;
	}
	 
	public String getAlias(String p_name,String p_type) throws Exception
	{
		AliasData l_data=getAliasses().get(p_name);
		if(l_data != null){			
			if(l_data.getType() != p_type){
				throw new InvalidAliasType(p_type,l_data.getType());
			}
			return l_data.getValue();
		}
		return null;
	}
	
	public String getAlias(String p_name,String p_type,boolean p_mandatory) throws Exception
	{
		String l_return=getAlias(p_name,p_type);
		if((l_return==null) && p_mandatory){
			throw new Errors.AliasNotFound(p_name);
		}
		return l_return;
	}

	public Url getUrlByAlias(String p_name) throws Exception
	{
		return new Url(getAlias(p_name,AliasData.alias_url,true));
	}
	
	public String getThemeBase(){
		return themeBase;
	}
	
	public void setThemeBase(String p_base)
	{
		themeBase=p_base;
	}
	
		
	public  Application() {		
	}

}
