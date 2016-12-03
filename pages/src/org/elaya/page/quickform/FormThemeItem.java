package org.elaya.page.quickform;

import java.io.IOException;
import org.elaya.page.Theme;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.Writer;

public abstract class FormThemeItem extends ThemeItemBase {
	
	public abstract void formFooterBegin(Writer p_writer) throws IOException;
	public abstract void FormFooterOk(Writer p_writer,String p_domId,String p_saveText) throws IOException;
	public abstract void FormFooterCancel(Writer p_writer,String p_domId,String p_cancelText) throws IOException;
	public abstract void FormFooterBetween(Writer p_writer) throws IOException;
	public abstract void formHeader(Writer p_writer,String p_domId,String p_title,String p_url,String p_method,String p_width) throws Exception;
	public abstract void formFooter(Writer p_writer) throws Exception;
	public abstract void formHiddenElement(Writer p_writer,String p_domId,String p_name,String p_value) throws IOException;

}
