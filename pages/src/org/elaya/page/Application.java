package org.elaya.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

public class Application {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String jsPath="pages/js/";
	private String cssPath="pages/css/";
	private String imgPath="pages/images/";
	private String themeBase="org.elaya.page.defaultTheme";
	Logger logger;
	
	public void setLogger(Logger p_logger)
	{
		logger=p_logger;
	}
	
	public Logger getLogger(){ return logger;}
	
	public HttpServletResponse getResponse()
	{
		return response;
	}
	
	public String getRealPath(String p_path)
	{
		return request.getSession().getServletContext().getRealPath(p_path);
	}
	
	public String getBasePath(){
		return request.getContextPath();
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
	
	public Application(HttpServletRequest p_request,HttpServletResponse p_response) {
		request=p_request;
		response=p_response;
	}

}
