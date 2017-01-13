package org.elaya.page.listmenu;

import java.io.IOException;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.Writer;

public abstract class ListMenuThemeItem extends ThemeItemBase {

	public abstract void header(Writer pwriter,String ptitle) throws IOException;
	public abstract void preItem(Writer pwriter) throws IOException;
	public abstract void preItemSelected(Writer pwriter) throws IOException;
	public abstract void linkItem(Writer pwriter,String pdomId,String ptext,String purl,String delUrl,String editUrl) throws IOException;
	public abstract void postItem(Writer pwriter) throws IOException;
	public abstract void footer(Writer pwriter) throws IOException;
	public abstract void groupHeader(Writer pwriter,String ptitle) throws IOException;
	public abstract void groupFooter(Writer pwriter)throws IOException;
	
}
