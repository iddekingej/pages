package org.elaya.page.core;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageSession {
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public PageSession(HttpServletRequest prequest,HttpServletResponse presponse){
		request=prequest;
		response=presponse;
	}
	
	public String getURIPath()
	{
		return request.getRequestURI().substring(request.getContextPath().length());
	}
	
	public String getRequestURI()
	{
		return request.getRequestURI();
	}
	
	public String getBasePath()
	{
		return request.getContextPath();
	}
	
	public ServletOutputStream getOutputStream() throws IOException
	{
		return response.getOutputStream();
	}
	
	public void setContentType(String ptype)
	{
		response.setContentType(ptype);
	}
}
