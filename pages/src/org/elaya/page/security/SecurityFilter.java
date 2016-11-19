package org.elaya.page.security;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SecurityFilter implements Filter {
	private SecurityManager securityManager;
	public SecurityFilter() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest p_request, ServletResponse p_response, FilterChain p_chain)
			throws IOException, ServletException {
		if(securityManager != null){
			if(securityManager.execute(p_request, p_response)){
				p_chain.doFilter(p_request, p_response);
			}
		}
	}

	@Override
	public void init(FilterConfig p_config) throws ServletException {
		String l_fileName=p_config.getInitParameter("securityConfigFile");
		XmlSecurityParser l_parser=new XmlSecurityParser();
		try {
			Object l_object=l_parser.parse(l_fileName);
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
				throw new ServletException("Security xml config '"+l_fileName+"' didn't define a security manager");
			}
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
		
		
	}

}
