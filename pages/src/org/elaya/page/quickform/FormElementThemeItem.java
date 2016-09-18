package org.elaya.page.quickform;

import java.io.IOException;
import java.util.LinkedList;
import org.elaya.page.Theme;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.Writer;

public abstract class FormElementThemeItem extends ThemeItemBase {

	abstract public void textElement(Writer p_writer,String p_idDom,String p_name,Object p_value,int p_maxLength) throws IOException;
	abstract public void radioElement(Writer p_writer,String p_idDom,String p_name,LinkedList<OptionItem> p_items,boolean p_isHorizontal,Object p_value) throws IOException;
	abstract public void checkBoxElement(Writer p_writer,String p_idDom,String p_name,Object p_value) throws IOException;
	abstract public void StaticElement(Writer p_writer,String p_idDom,String p_name,boolean p_isHtml,Object p_value) throws IOException;
	abstract public void selectElement(Writer p_writer,String p_idDom,String p_name,LinkedList<OptionItem> p_items,Object p_value) throws IOException;
	abstract public void textArea(Writer p_writer,String p_idDom,String p_name,String p_height,String p_width,Object p_value) throws IOException;
	public abstract void dateElement(Writer p_writer,String p_domId,String p_name,Object l_value) throws IOException;

	public FormElementThemeItem(Theme p_theme) throws IOException {
		super(p_theme);		
	}

}
