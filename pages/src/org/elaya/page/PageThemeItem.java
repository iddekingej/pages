package org.elaya.page;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public abstract class PageThemeItem extends ThemeItemBase {

	abstract public void pageHeader() throws IOException ;
	abstract public void pageFooter() throws IOException ;
	
	public PageThemeItem(HttpServletResponse p_response) throws IOException {
		super(p_response);
	}

}
