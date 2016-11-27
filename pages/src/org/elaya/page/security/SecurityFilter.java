package org.elaya.page.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SecurityFilter implements Filter {

	private SecurityFilterBackend backend=new SecurityFilterBackend();
	public SecurityFilter() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public void doFilter(ServletRequest p_request, ServletResponse p_response, FilterChain p_chain)
			throws IOException, ServletException {
		backend.doFilter(p_request, p_response, p_chain);
	}

	@Override
	public void init(FilterConfig p_config) throws ServletException {
		backend.init(p_config,p_config.getInitParameter("securityConfigFile"));
	}

	@Override
	public void destroy() {
		
	}

}
