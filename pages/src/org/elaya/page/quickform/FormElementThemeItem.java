package org.elaya.page.quickform;

import java.io.IOException;
import java.util.LinkedList;
import org.elaya.page.Theme;
import org.elaya.page.ThemeItemBase;

public abstract class FormElementThemeItem extends ThemeItemBase {

	abstract public void textElement(String p_idDom,String p_name,Object p_value) throws IOException;
	abstract public void radioElement(String p_idDom,String p_name,LinkedList<OptionItem> p_items,boolean p_isHorizontal,Object p_value) throws IOException;
	abstract public void checkBoxElement(String p_idDom,String p_name,Object p_value) throws IOException;
	abstract public void StaticElement(String p_idDom,String p_name,boolean p_isHtml,Object p_value) throws IOException;
	abstract public void selectElement(String p_idDom,String p_name,LinkedList<OptionItem> p_items,Object p_value) throws IOException;

	public FormElementThemeItem(Theme p_theme) throws IOException {
		super(p_theme);		
	}

}
