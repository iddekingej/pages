package org.elaya.page.table;

import org.elaya.page.Element;
import org.elaya.page.ThemeItemBase;

public abstract class TableElement<T extends ThemeItemBase> extends Element<T> {
	private String title;
	public String getTitle(){ return title;}
	public void setTitle(String ptitle){ title=ptitle;}

}
