package org.elaya.page.table;

import java.io.IOException;

import org.elaya.page.ThemeItemBase;
import org.elaya.page.Writer;

public abstract class TableThemeItem extends ThemeItemBase {
	public abstract void tableHeader(Writer pwriter,String pdomId) throws IOException;
	public abstract void tableFooter(Writer pwriter) throws IOException;
	public abstract void titleHeader(Writer pwriter) throws IOException;
	public abstract void title(Writer pwriter,String ptitle) throws IOException;
	public abstract void titleFooter(Writer pwriter) throws IOException;
	public abstract void rowHeader(Writer pwriter) throws IOException;
	public abstract void rowFooter(Writer pwriter) throws IOException;
	public abstract void itemHeader(Writer pwriter) throws IOException;
	public abstract void itemFooter(Writer pwriter) throws IOException;
	public abstract void staticItem(Writer pwriter,String pdomId,Object ptext) throws IOException;
	public abstract void linkItem(Writer pwriter,String purl,String ptext) throws IOException;
	
}
