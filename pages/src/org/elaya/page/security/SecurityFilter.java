package org.elaya.page.security;
 
import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.elaya.page.application.Application;

public class SecurityFilter implements Filter {
	private SecurityManager securityManager;

	@Override
	public void doFilter(ServletRequest prequest, ServletResponse presponse, FilterChain chain)
			throws IOException, ServletException {
		if(securityManager != null){
			try {
				if(securityManager.execute(prequest, presponse)){
					chain.doFilter(prequest, presponse);
				}
			} catch (Exception e) {				
				throw new ServletException("doFilter failed",e);
			} 
		}	}

	public void initParser(XmlSecurityParser pparser)
	{
		/* Can be used to initialized parser further */
	}
	@Override
	public void init(FilterConfig pconfig) throws ServletException {
		Application application;
		System.out.println("Security filter init start");
		String filterFileName=pconfig.getInitParameter("securityConfigFile");
		Object appObject=pconfig.getServletContext().getAttribute("application");		
		if(appObject instanceof Application){
			application=(Application)appObject;
		} else {
			throw new ServletException("Application not set");
		}
		XmlSecurityParser parser=new XmlSecurityParser(application);
		initParser(parser);
		List<String> errors;
		Object object;
		try {			
			object=parser.parse(filterFileName);
			System.out.println("End parsing security xml");
			errors=parser.getErrors();
		} catch (Exception e) {
			throw new ServletException("Filter initalisation failed",e);
		}	
		
		if(!errors.isEmpty()){
			StringBuilder errorStr=new StringBuilder();
			for(String error:errors){
				errorStr.append("\n").append(error);
			}
			throw new ServletException(errorStr.toString());
		}
		if(object instanceof SecurityManager){
			securityManager=(SecurityManager)object;				
		} else {
			throw new ServletException("Security xml config '"+filterFileName+"' didn't define a security manager");
		}
		System.out.println("Security filter finished");
	}

	@Override
	public void destroy() {
		/* nothing to destroy */
	}

}
