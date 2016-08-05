package org.elaya.page;

import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.http.HttpServletResponse;

public abstract class FormElementThemeItem extends ThemeItemBase {

	abstract public void textElement(String p_name,String p_value) throws IOException;
	abstract public void radioElement(String p_name,LinkedList<OptionItem> p_items,boolean p_isHorizontal) throws IOException;

	public FormElementThemeItem(HttpServletResponse p_response) throws IOException {
		super(p_response);
		// TODO Auto-generated constructor stub
	}

}
