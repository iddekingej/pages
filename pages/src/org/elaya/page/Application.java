package org.elaya.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Application {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String jsPath="pages/js/";
	private String cssPath="pages/css/";
	private String imgPath="pages/images/";
	private String themeBase="org.elaya.page.defaultTheme";
	
	public HttpServletResponse getResponse()
	{
		return response;
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
