package org.elaya.page.table;

import java.io.IOException;

import org.elaya.page.Theme;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.Writer;

abstract public class TableThemeItem extends ThemeItemBase {

	public TableThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}
	
	abstract public void tableHeader(Writer p_writer,String p_domId) throws IOException;
	abstract public void tableFooter(Writer p_writer) throws IOException;
	abstract public void titleHeader(Writer p_writer) throws IOException;
	abstract public void title(Writer p_writer,String p_title) throws IOException;
	abstract public void titleFooter(Writer p_writer) throws IOException;
	abstract public void rowHeader(Writer p_writer) throws IOException;
	abstract public void rowFooter(Writer p_writer) throws IOException;
	abstract public void itemHeader(Writer p_writer) throws IOException;
	abstract public void itemFooter(Writer p_writer) throws IOException;
	abstract public void staticItem(Writer p_writer,String p_domId,Object p_text) throws IOException;
	abstract public void linkItem(Writer p_writer,String p_url,String p_text) throws IOException;
	
}
