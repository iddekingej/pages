package org.elaya.page;

import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.elaya.page.data.Context;
import org.slf4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.ServletContextAware;

public class Application implements  ServletContextAware {
	private Context context=null;
	private HttpServletRequest request;
	private String jsPath="resources/pages/js/";
	private String cssPath="resources/pages/css/";
	private String imgPath="resources/pages/images/";
	private String themeBase="org.elaya.page.defaultTheme";	
	Logger logger;
	private ServletContext servletContext;
	private String aliasFiles;
	private HashMap<String,Page> pageCache=new HashMap<>(); 
	private HashMap<String,String> aliasses;
	
	public void setLogger(Logger p_logger)
	{
		logger=p_logger;
	}
	public HashMap<String,String> getAliasses() throws Exception
	{
		if(aliasses==null){
			loadAliasFiles();
		}
		logger.info("Bla:"+aliasses.size());
		return aliasses;
	}
	 
/* Page handling */
	
	public synchronized Page loadPage(String p_fileName) throws Exception
	{
		if(pageCache.containsKey(p_fileName)){
			return pageCache.get(p_fileName);
		}
		UiXmlParser l_parser=new UiXmlParser(this,getAliasses(),logger);
		Page l_page=l_parser.parseUiXml(p_fileName);
		LinkedList<String> l_errors=l_parser.getErrors();
		if(l_errors.size()>0){
			for(String l_error:l_errors){
				logger.info(p_fileName+":"+l_error);
			}
			throw new Errors.LoadingPageFailed(p_fileName);
		}

		if(l_page != null){
			pageCache.put(p_fileName, l_page);
		}
		return l_page;
	}
	
/* Alias handling */
	private void addAliasses(String p_fileName) throws Exception
	{
		AliasParser l_parser=new AliasParser(logger);
		logger.info("Parse alias file :"+p_fileName);
		l_parser.parseAliases(servletContext.getResourceAsStream(p_fileName), aliasses);		
		for(String l_error:l_parser.getErrors()){
			logger.info(l_error);
		}
	}
	
	public void setAliasFiles(String p_aliasFiles) 
	{
		aliasFiles=p_aliasFiles;
	}
	
	private void loadAliasFiles() throws Exception
	{
		aliasses=new HashMap<String,String>();
		String[] l_fileNames=aliasFiles.split(",");
		logger.info("alias "+aliasFiles);
		for(String l_fileName:l_fileNames){
			addAliasses(l_fileName);
		}
	}
	
	public DriverManagerDataSource getDB(String p_name)
	{
		return getContext().getDb(p_name);
	}
	
	public Context getContext()
	{
		if(context==null){
			context=new Context();
		}
		return context;
	}
	
	public String getRealPath(String p_path) throws Exception
	{
		if(servletContext==null) throw new Exception("Bla");
		return servletContext.getRealPath(p_path);
	}
	
	public String getBasePath(){
		return request.getContextPath();
	}
	
	public String procesUrl(String p_url)
	{
		if(p_url.startsWith("+")){
			return getBasePath()+"/"+p_url.substring(1);
		} else {
			return p_url;
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

	@Override
	public void setServletContext(ServletContext p_servletContext) {
		servletContext=p_servletContext;
	}

}
