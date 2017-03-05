package org.elaya.page.widget.element;

import java.io.IOException;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.core.Writer;

public abstract class ElementThemeItem extends ThemeItemBase {

	public abstract void staticElement(Writer pwriter,String ptext,boolean pisHtml,String pclass,String pcss) throws IOException;
	public abstract void image(Writer pwriter,String purl,String pclass,String pcss) throws IOException;
	/** Display Header of a panel
	 * 
	 * @param pwriter  Output writer for generating html
	 * @param pclass   Class name used in <class> tag
	 * @param pcss     css of panel
	 * @throws IOException
	 */
	public abstract void panelHeader(Writer pwriter,String pcss) throws IOException;
	public abstract void panelFooter(Writer pwriter) throws IOException;
	public abstract void link(Writer pwriter,String purl,String ptext,String pclass,String pcss) throws IOException;
	public abstract void verticalSpacer(Writer pwriter) throws IOException;
	public abstract void horizontalSpacer(Writer pwriter) throws IOException;
	public abstract void menuBarHeader(Writer pwriter) throws IOException;
	public abstract void menuBarItemHeader(Writer pwriter)throws IOException;
	public abstract void menuBarItemFooter(Writer pwriter)throws IOException;
	public abstract void menuBarFooter(Writer pwriter) throws IOException;
	public abstract void menu(Writer pwriter,String pid,String ptitle) throws IOException;
	public abstract void button(Writer writer,String id,String text) throws IOException;
	public abstract void olmapdiv(Writer writer,String id,int pdivWidth,int pdivHeight) throws IOException;
}
