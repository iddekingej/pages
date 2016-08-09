package org.elaya.page.form;

import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.Theme;
import org.elaya.page.ThemeItemBase;

public abstract class FormElementThemeItem extends ThemeItemBase {

	abstract public void textElement(String p_name,String p_value) throws IOException;
	abstract public void radioElement(String p_name,LinkedList<OptionItem> p_items,boolean p_isHorizontal,String p_value) throws IOException;
	abstract public void checkBoxElement(String p_name,String p_value) throws IOException;
	abstract public void StaticElement(String p_name,boolean p_isHtml,String p_value) throws IOException;
	abstract public void selectElement(String p_name,LinkedList<OptionItem> p_items,String p_value) throws IOException;

	public FormElementThemeItem(Theme p_theme) throws IOException {
		super(p_theme);		
	}

}
