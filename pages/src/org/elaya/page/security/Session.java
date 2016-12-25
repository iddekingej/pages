package org.elaya.page.security;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.elaya.page.data.DynamicObject;


public class Session {
	public static class InvalidSessionData extends Exception{

		private static final long serialVersionUID = 1L;
		public InvalidSessionData(String pmessage){
			super(pmessage);
		}
	}
	
	private ServletRequest request;
	private ServletResponse response;
	private HttpServletRequest httpRequest;
	private HttpServletResponse httpResponse;
	private AuthorizationData authorisationData;
	
	public Session(ServletRequest prequest,ServletResponse presponse)  {
		request=prequest;
		response=presponse;

	}
	protected void afterCreateSession(AuthorizationData authorisationData){
		/*Setup authorization data */
	}
	
	public void initSession() throws NotSerializableException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InvalidSessionData
	{
		if(request instanceof HttpServletRequest){
			httpRequest=(HttpServletRequest)request;
		}
		if(response instanceof HttpServletResponse){
			httpResponse=(HttpServletResponse)response;
		}
		initAuthorisation();
	}
	
	private AuthorizationData initAuthorisationData(Object objectId,Object typeObject,ServletRequest request) throws InvalidSessionData, NotSerializableException, InstantiationException, IllegalAccessException,  InvocationTargetException, NoSuchMethodException,  ClassNotFoundException
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
			
		if(httpRequest != null){			
			HttpSession httpSession=httpRequest.getSession(false);
			if(httpSession != null){
				Object typeObject=httpSession.getAttribute("type");
				Object objectId=httpSession.getAttribute("id");
				if(objectId != null && typeObject != null){
					initAuthorisationData(objectId,typeObject,request);					
				}
			}
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
	
	public void setAuthorisationData(AuthorizationData pauthorisationData)
	{
		authorisationData=pauthorisationData;
		request.setAttribute("org.elaya.page.security.SessionData", pauthorisationData);
	}
	
	public AuthorizationData getAuthorisationData()
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
	
	public void redirect(String url) throws IOException
	{
		if(httpRequest != null && httpResponse != null){
			httpResponse.sendRedirect(httpRequest.getContextPath()+url);
		}

	}
}
