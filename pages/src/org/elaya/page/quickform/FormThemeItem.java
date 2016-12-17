package org.elaya.page.quickform;

import java.io.IOException;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.Writer;

public abstract class FormThemeItem extends ThemeItemBase {
	
	public abstract void formFooterBegin(Writer pwriter) throws IOException;
	public abstract void formFooterOk(Writer pwriter,String pdomId,String psaveText) throws IOException;
	public abstract void formFooterCancel(Writer pwriter,String pdomId,String pcancelText) throws IOException;
	public abstract void formFooterBetween(Writer pwriter) throws IOException;
	public abstract void formHeader(Writer pwriter,String pdomId,String ptitle,String purl,String pmethod,String pwidth) throws IOException ;
	public abstract void formFooter(Writer pwriter) throws IOException ;
	public abstract void formHiddenElement(Writer pwriter,String pdomId,String pname,String pvalue) throws IOException;

}
