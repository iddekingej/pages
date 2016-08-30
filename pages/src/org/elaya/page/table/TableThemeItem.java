package org.elaya.page.table;

import java.io.IOException;

import org.elaya.page.Theme;
import org.elaya.page.ThemeItemBase;

abstract public class TableThemeItem extends ThemeItemBase {

	public TableThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}
	
	abstract public void tableHeader(String p_domId) throws IOException;
	abstract public void tableFooter() throws IOException;
	abstract public void titleHeader() throws IOException;
	abstract public void title(String p_title) throws IOException;
	abstract public void titleFooter() throws IOException;
	abstract public void rowHeader() throws IOException;
	abstract public void rowFooter() throws IOException;
	abstract public void itemHeader() throws IOException;
	abstract public void itemFooter() throws IOException;
	abstract public void staticItem(String p_domId,Object p_text) throws IOException;
	abstract public void linkItem(String p_url,String p_text) throws IOException;
	
}
