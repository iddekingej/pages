package org.elaya.page.security;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.elaya.page.application.Application;

public class SecurityFilterBackend {
	private SecurityManager securityManager;
	private Application application;
	
	public Application getApplication(){
		return application;
	}
	
	public SecurityFilterBackend() {
		// TODO Auto-generated constructor stub
	}

	public void doFilter(ServletRequest p_request, ServletResponse p_response, FilterChain p_chain)
			throws IOException, ServletException {
		if(securityManager != null){
			try {
				if(securityManager.execute(p_request, p_response)){
					p_chain.doFilter(p_request, p_response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e.getMessage() +" in "+e.getStackTrace());
			} 
		}
	}	
	
	protected void initParser(XmlSecurityParser p_parser)
	{
		
	}
	
	public void init(FilterConfig p_config,String p_filterFileName) throws ServletException {
		application=null;
		Object l_AppObject=p_config.getServletContext().getAttribute("application");		
		if(l_AppObject instanceof Application){
			application=(Application)l_AppObject;
		} else {
			//TODO error when no application
		}
		XmlSecurityParser l_parser=new XmlSecurityParser(application);;
		initParser(l_parser);
		try {
			Object l_object=l_parser.parse(p_filterFileName);
			LinkedList<String> l_errors=l_parser.getErrors();
			if(!l_errors.isEmpty()){
				String l_errorStr="";
				for(String l_error:l_errors){
					l_errorStr=l_errorStr+"\n"+l_error;
				}
				throw new ServletException(l_errorStr);
			}
			if(l_object instanceof SecurityManager){
				securityManager=(SecurityManager)l_object;				
			} else {
				throw new ServletException("Security xml config '"+p_filterFileName+"' didn't define a security manager");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage()+" in "+e.getStackTrace().toString());
		}
		
		
	}
	
}
