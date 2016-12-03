package org.elaya.page.listmenu;

import java.io.IOException;

import org.elaya.page.Theme;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.Writer;

abstract public class ListMenuThemeItem extends ThemeItemBase {

	abstract public void header(Writer p_writer,String p_title) throws IOException;
	abstract public void preItem(Writer p_writer) throws IOException;
	abstract public void preItemSelected(Writer p_writer) throws IOException;
	abstract public void linkItem(Writer p_writer,String p_domId,String p_text,String p_url) throws IOException;
	abstract public void postItem(Writer p_writer) throws IOException;
	abstract public void footer(Writer p_writer) throws IOException;
	abstract public void groupHeader(Writer p_writer,String p_title) throws IOException;
	abstract public void groupFooter(Writer p_writer)throws IOException;
	
}
