package org.elaya.page.application;

import java.util.HashMap;
import java.util.LinkedList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.elaya.page.AliasData;
import org.elaya.page.AliasParser;
import org.elaya.page.Errors;
import org.elaya.page.Initializer;
import org.elaya.page.Page;
import org.elaya.page.UiXmlParser;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Application{
	private HttpServletRequest request;
	private String jsPath="resources/pages/js/";
	private String cssPath="resources/pages/css/";
	private String imgPath="resources/pages/images/";
	private String themeBase="org.elaya.page.defaultTheme";	
	private Logger logger;
	private ServletContext servletContext;
	private String aliasFiles;
	private HashMap<String,Page> pageCache=new HashMap<>(); 
	private HashMap<String,AliasData> aliasses;
	private ApplicationContext DBContext=null;
	private HashMap<String,DriverManagerDataSource> dbConnections=new HashMap<>();
	static private LinkedList<Initializer> initializers=new LinkedList<>();
	
	class InvalidAliasType extends Exception
	{

		private static final long serialVersionUID = -7739276897593055425L;

		public InvalidAliasType(String p_typeReq,String p_typeGot)
		{
			super("Invalid alias type, '"+p_typeReq+"' expected but '"+p_typeGot+"' found");
		}
	}
	//--(initializers )-----------------------------------------
	
	static public void initilizeObject(Object p_object)
	{
		for(Initializer l_item:initializers){
			l_item.process(p_object);
		}
	}
	public void addInitializer(Initializer p_initializer)
	{
		initializers.add(p_initializer);
	}
	
	static 	public void addInitializerStatic(Initializer p_initializer)
	{
		initializers.add(p_initializer);
	}
	
	//--(db)------------------------------------------------------------------------
	
	private ApplicationContext getDBContext()
	{
		if(DBContext ==null){
			DBContext=new ClassPathXmlApplicationContext("../database/database.xml");
		}
		return DBContext;
	}
	
	public DriverManagerDataSource getDB(String p_name)
	{
		if(dbConnections.containsKey(p_name)){
			return dbConnections.get(p_name);
		}
		DriverManagerDataSource l_db=getDBContext().getBean(p_name,DriverManagerDataSource.class);
		dbConnections.put(p_name, l_db);
		return l_db;
	}
	
	
	//logger
	public void setLogger(Logger p_logger)
	{
		logger=p_logger;
	}
	
	public Logger getLogger()
	{
		return logger;
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
		if(p_cache){
			if(pageCache.containsKey(p_fileName)){
				return pageCache.get(p_fileName);
			}
		}
		UiXmlParser l_parser=new UiXmlParser(this,getClass().getClassLoader());
		Object l_object=l_parser.parse(p_fileName);

		
		LinkedList<String> l_errors=l_parser.getErrors();
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
			if(l_page != null){
				pageCache.put(p_fileName, l_page);
			}
		}
		return l_page;
	}
	
/* Alias handling */
//TODO error handling
	private void addAliasses(String p_fileName) throws Exception
	{
		AliasParser l_parser=new AliasParser();

		l_parser.parseAliases(getClass().getClassLoader().getResourceAsStream("../pages/"+p_fileName), aliasses);
		
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
	//--
	public String getRealPath(String p_path) throws Exception
	{
		if(servletContext==null) throw new Exception("Bla");
		return servletContext.getRealPath(p_path);
	}
	
	public String getBasePath(){
		return request.getContextPath();
	}
	
	public String procesUrl(String p_url) throws Exception
	{
		String l_url=p_url;
		if(l_url.startsWith("@")){
			l_url=getAlias(l_url.substring(1),AliasData.alias_url,true);
		}
		if(l_url.startsWith("+")){
			return getBasePath()+"/"+l_url.substring(1);
		} else {
			return l_url;
		}
	}
	
	public String getJsPath(String p_file)
	{
		return getBasePath()+"/"+jsPath+p_file;
	}
	
	public String getCssPath(String p_file)
	{
		return getBasePath()+"/"+cssPath+p_file;
	}
	
	public String getImgPath(String p_file)
	{
		return getBasePath()+"/"+imgPath+p_file;
	}
	
	
	
	public String getThemeBase(){
		return themeBase;
	}
	
	public void setThemeBase(String p_base)
	{
		themeBase=p_base;
	}
	
	public void setRequest(HttpServletRequest p_request)
	{
		request=p_request;
	}
	
	public HttpServletRequest  getRequest()
	{
		return request;
	}
		
	public  Application() {		
	}

	void setServletContext(ServletContext p_servletContext) {
		servletContext=p_servletContext;
	}

}