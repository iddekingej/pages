package org.elaya.page.widget.table;

import org.elaya.page.ThemeItemBase;
import org.elaya.page.widget.Element;

public abstract class TableElement<T extends ThemeItemBase> extends Element<T> {
	private String title;
	public String getTitle(){ return title;}
	public void setTitle(String ptitle){ title=ptitle;}

}
