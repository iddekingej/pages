package org.elaya.page.element;

import java.io.IOException;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.Writer;

public abstract class ElementThemeItem extends ThemeItemBase {

	public abstract void staticElement(Writer p_writer,String p_text,boolean p_isHtml,String p_class,String p_css) throws IOException;
	public abstract void image(Writer p_writer,String p_url,String p_class,String p_css) throws IOException;
	public abstract void panelHeader(Writer p_writer,String p_class,String p_css) throws IOException;
	public abstract void panelFooter(Writer p_writer) throws IOException;
	public abstract void link(Writer p_writer,String p_url,String p_text,String p_class,String p_css) throws IOException;
	public abstract void verticalSpacer(Writer p_writer) throws IOException;
	public abstract void horizontalSpacer(Writer p_writer) throws IOException;
	public abstract void MenubarHeader(Writer p_writer,String p_domId) throws IOException;
	public abstract void MenubarFooter(Writer p_writer) throws IOException;
	public abstract void SubMenuHeader(Writer p_writer,String p_icon,String p_title)  throws IOException;
	public abstract void SubMenuFooter(Writer p_writer)  throws IOException;
	public abstract void MenuItem(Writer p_writer,String p_icon,String p_title, String p_url)  throws IOException;
	public abstract void menuBarHeader(Writer p_writer) throws IOException;
	public abstract void menuBarItemHeader(Writer p_writer)throws IOException;
	public abstract void menuBarItemFooter(Writer p_writer)throws IOException;
	public abstract void menuBarFooter(Writer p_writer) throws IOException;
	public abstract void menu(Writer p_writer,String p_id,String p_title) throws IOException;
	public abstract void menuSeperator(Writer p_writer) throws IOException;
}
