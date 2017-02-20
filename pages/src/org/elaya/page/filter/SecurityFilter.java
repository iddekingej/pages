package org.elaya.page.filter;
 
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.elaya.page.application.Application;

public class SecurityFilter implements Filter {
	private FilterManager securityManager;

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
		}	
	}

	public void initParser(XmlFilterParser pparser)
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
		XmlFilterParser parser=new XmlFilterParser();
		parser.setApplication(application);
		initParser(parser);		
		try {			
			securityManager=parser.parse(filterFileName,FilterManager.class);
		} catch (Exception e) {
			throw new ServletException("Filter initalisation failed",e);
		}	
		
		System.out.println("Security filter finished");
	}

	@Override
	public void destroy() {
		/* nothing to destroy */
	}

}
