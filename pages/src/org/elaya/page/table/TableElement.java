package org.elaya.page.table;

import org.elaya.page.Element;
import org.elaya.page.ThemeItemBase;

abstract public class TableElement<themeType extends ThemeItemBase> extends Element<themeType> {
	private String title;
	public String getTitle(){ return title;}
	public void setTitle(String p_title){ title=p_title;}
	public void display() throws Exception {
		throw new Exception("Display(Object) should be called");

	}
}
