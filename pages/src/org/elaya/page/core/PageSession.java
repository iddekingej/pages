package org.elaya.page.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.elaya.page.data.DynamicObject;

public class PageSession {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AuthorizationData authorisationData;
	
	public static class InvalidSessionData extends Exception
	{
		private static final long serialVersionUID = 1L;

		public InvalidSessionData(String pmessage){
			super(pmessage);
		}
	}
	
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
	
	public void redirect(String url) throws IOException
	{
		if(request != null && response != null){
			response.sendRedirect(request.getContextPath()+url);
		}

	}
	
	public HttpSession getHttpSession(boolean pnew)
	{
		return request.getSession(pnew);
	}
	
	public void initSession() throws NotSerializableException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InvalidSessionData
	{
		initAuthorisation();
	}
	
	protected void afterCreateSession(AuthorizationData authorisationData){
		/*Setup authorization data */
	}
	
	private AuthorizationData initAuthorisationData(Object objectId,Object typeObject,HttpServletRequest request) throws InvalidSessionData, NotSerializableException, InstantiationException, IllegalAccessException,  InvocationTargetException, NoSuchMethodException,  ClassNotFoundException
	{
		Serializable id;
		authorisationData=null;
		if(objectId instanceof Serializable ){
			id=(Serializable)objectId;
		} else {
			throw new NotSerializableException("ID attribute of session is not Serializable (type="+objectId.getClass().getName()+")");
		}
		Object object=DynamicObject.createObjectFromName(typeObject.toString());
		if(object instanceof AuthorizationData){
			setAuthorisationData((AuthorizationData)object);
			afterCreateSession(authorisationData);
			authorisationData.initSessionData(id);
		} else {
			throw new InvalidSessionData("Sessiondata object (type="+object.getClass().getName()+") doesn't descent from SessionData");
		}
		request.setAttribute("org.elaya.page.security.SessionData", authorisationData);
		return authorisationData;
	}	
	private void initAuthorisation() throws NotSerializableException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InvalidSessionData
	{
		
			HttpSession httpSession=request.getSession(false);
			if(httpSession != null){
				Object typeObject=httpSession.getAttribute("type");
				Object objectId=httpSession.getAttribute("id");
				if(objectId != null && typeObject != null){
					initAuthorisationData(objectId,typeObject,request);					
				}
			}
		
	}
	

	public void setAuthorisationData(AuthorizationData pauthorisationData)
	{
		authorisationData=pauthorisationData;
		request.setAttribute("org.elaya.page.security.SessionData", pauthorisationData);
	}
	
	public AuthorizationData getAuthorisationData()
	{
		return authorisationData;
	}
	
	public String getParameter(String pname)
	{
		return request.getParameter(pname);
	}
	
	public void setAttribute(String pname,Object pvalue)
	{
		request.setAttribute(pname, pvalue);
	}
	
	public String getPathInfo()
	{
		return request.getPathInfo();
	}
	
	public String getMethod()
	{
		return request.getMethod();
	}
	
	public BufferedReader getReader() throws IOException
	{
		return request.getReader();
	}
}
