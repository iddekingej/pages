package org.elaya.page.element;

import java.io.IOException;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.Writer;

public abstract class ElementThemeItem extends ThemeItemBase {

	public abstract void staticElement(Writer pwriter,String ptext,boolean pisHtml,String pclass,String pcss) throws IOException;
	public abstract void image(Writer pwriter,String purl,String pclass,String pcss) throws IOException;
	public abstract void panelHeader(Writer pwriter,String pclass,String pcss) throws IOException;
	public abstract void panelFooter(Writer pwriter) throws IOException;
	public abstract void link(Writer pwriter,String purl,String ptext,String pclass,String pcss) throws IOException;
	public abstract void verticalSpacer(Writer pwriter) throws IOException;
	public abstract void horizontalSpacer(Writer pwriter) throws IOException;
	public abstract void menuBarHeader(Writer pwriter) throws IOException;
	public abstract void menuBarItemHeader(Writer pwriter)throws IOException;
	public abstract void menuBarItemFooter(Writer pwriter)throws IOException;
	public abstract void menuBarFooter(Writer pwriter) throws IOException;
	public abstract void menu(Writer pwriter,String pid,String ptitle) throws IOException;
	public abstract void menuSeperator(Writer pwriter) throws IOException;
}
