package org.elaya.page;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public abstract class FormThemeItem extends ThemeItemBase {
	
	public abstract void formHeader(String p_title,String p_url) throws Exception;
	public abstract void formFooter() throws Exception;
	public abstract void formElementBegin(String p_label) throws Exception;
	public abstract void formElementEnd() throws Exception;
	
	public FormThemeItem(Theme p_theme) throws IOException {
		super(p_theme);		
	}
	
	
}
