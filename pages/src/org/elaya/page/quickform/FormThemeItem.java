package org.elaya.page.quickform;

import java.io.IOException;
import org.elaya.page.Theme;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.Writer;

public abstract class FormThemeItem extends ThemeItemBase {
	
	public abstract void formHeader(Writer p_writer,String p_domId,String p_title,String p_url,String p_method,String p_width) throws Exception;
	public abstract void formFooter(Writer p_writer,String p_domId,String p_saveText,String p_submitJs) throws Exception;
	public abstract void formElementBegin(Writer p_writer,String p_label) throws Exception;
	public abstract void formElementEnd(Writer p_writer) throws Exception;
	
	public FormThemeItem(Theme p_theme) throws IOException {
		super(p_theme);		
	}
	
	
}
