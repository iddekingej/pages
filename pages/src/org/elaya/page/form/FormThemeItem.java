package org.elaya.page.form;

import java.io.IOException;
import org.elaya.page.Theme;
import org.elaya.page.ThemeItemBase;

public abstract class FormThemeItem extends ThemeItemBase {
	
	public abstract void formHeader(String p_domId,String p_title,String p_url) throws Exception;
	public abstract void formFooter(String p_domId) throws Exception;
	public abstract void formElementBegin(String p_label) throws Exception;
	public abstract void formElementEnd() throws Exception;
	
	public FormThemeItem(Theme p_theme) throws IOException {
		super(p_theme);		
	}
	
	
}
