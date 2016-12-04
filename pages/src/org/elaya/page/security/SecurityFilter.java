package org.elaya.page.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.elaya.page.application.Application;

public class SecurityFilter implements Filter {
	private SecurityManager securityManager;
	private Application application;
	
	public SecurityFilter() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
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
		}	}

	public void initParser(XmlSecurityParser p_parser)
	{
		
	}
	@Override
	public void init(FilterConfig p_config) throws ServletException {
		application=null;
		String l_filterFileName=p_config.getInitParameter("securityConfigFile");
		Object l_AppObject=p_config.getServletContext().getAttribute("application");		
		if(l_AppObject instanceof Application){
			application=(Application)l_AppObject;
		} else {
			throw new ServletException("Application not set");
		}
		XmlSecurityParser l_parser=new XmlSecurityParser(application);;
		initParser(l_parser);
		
		try {
			Object l_object=l_parser.parse(l_filterFileName);
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
				throw new ServletException("Security xml config '"+l_filterFileName+"' didn't define a security manager");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage()+" in "+e.getStackTrace().toString());
		}		
	}

	@Override
	public void destroy() {
		
	}

}
