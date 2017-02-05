package org.elaya.page.widget.element;

import java.io.IOException;

import org.elaya.page.ThemeItemBase;
import org.elaya.page.core.Writer;

public abstract class TabPageTheme extends ThemeItemBase {
	public abstract void pageHeader(Writer pwriter,String pdomId) throws IOException;
	public abstract void tabItem(Writer pwriter,String ptitle,String pdomId) throws IOException;
	public abstract void headerEnd(Writer pwriter,int ptabs) throws IOException;
	public abstract void pageFooter(Writer pwriter) throws IOException;
	public abstract void pageItemHeader(Writer pwriter,String pdomId) throws IOException;
	public abstract void pageItemFooter(Writer pwriter) throws IOException;
}
