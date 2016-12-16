package org.elaya.page.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Session {
	
	private ServletRequest request;
	private ServletResponse response;
	private HttpServletRequest httpRequest;
	private HttpServletResponse httpResponse;
	private AuthorisationData authorisationData;
	
	public Session(ServletRequest prequest,ServletResponse presponse) {
		request=prequest;
		if(request instanceof HttpServletRequest){
			httpRequest=(HttpServletRequest)request;
		}
		response=presponse;
		if(response instanceof HttpServletResponse){
			httpResponse=(HttpServletResponse)response;
		}
	}
	
	public ServletRequest getRequest(){
		return request;
	}
	
	public HttpServletRequest getHttpRequest(){
		return httpRequest;
	}
	
	public ServletResponse getResponse(){
		return response;
	}

	public HttpServletResponse getHttpResponse(){
		return httpResponse;
	}
	
	public void setAuthorisationData(AuthorisationData pauthorisationData)
	{
		authorisationData=pauthorisationData;
		request.setAttribute("org.elaya.page.security.SessionData", pauthorisationData);
	}
	
	public AuthorisationData getAuthorisationData()
	{
		return authorisationData;
	}
	
	public HttpSession getHttpSession(boolean pnew)
	{
		if(httpRequest!=null){
			return httpRequest.getSession(pnew);
		}
		return null;
	}
}
