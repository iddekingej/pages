package org.elaya.page.element;

import java.io.IOException;

import org.elaya.page.Theme;
import org.elaya.page.ThemeItemBase;

public abstract class ElementThemeItem extends ThemeItemBase {

	public ElementThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

	public abstract void  staticElement(String p_text,boolean p_isHtml,String p_class,String p_css) throws IOException;
	public abstract void  image(String p_url,String p_class,String p_css) throws IOException;
	public abstract void  panelHeader(String p_class,String p_css) throws IOException;
	public abstract void  panelFooter() throws IOException;
	public abstract void  link(String p_url,String p_text,String p_class,String p_css) throws IOException;
}
