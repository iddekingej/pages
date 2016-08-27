package org.elaya.page;

import java.io.IOException;
import java.util.Set;


public abstract class PageThemeItem extends ThemeItemBase {

	abstract public void pageHeader(Set<String> p_js,Set<String> p_css) throws IOException;
	abstract public void pageFooter() throws IOException ;
	
	public PageThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

}
