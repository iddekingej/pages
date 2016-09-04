package org.elaya.page.listmenu;

import java.io.IOException;

import org.elaya.page.Theme;
import org.elaya.page.ThemeItemBase;

abstract public class ListMenuThemeItem extends ThemeItemBase {

	public ListMenuThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

	abstract public void header(String p_title) throws IOException;
	abstract public void preItem() throws IOException;
	abstract public void preItemSelected() throws IOException;
	abstract public void linkItem(String p_domId,String p_text,String p_url) throws IOException;
	abstract public void postItem() throws IOException;
	abstract public void footer() throws IOException;
	abstract public void groupHeader(String p_title) throws IOException;
	abstract public void groupFooter()throws IOException;
	
}
