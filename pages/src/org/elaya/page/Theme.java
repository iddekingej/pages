package org.elaya.page;

import java.lang.reflect.InvocationTargetException;

import org.elaya.page.application.Application;

public class Theme {
	private String baseName;
	private String defaultTheme;

	
	public ThemeItemBase getTheme(String p_name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Class<?> l_class;
		try{
			l_class=Class.forName(baseName+"."+p_name);
		}catch(ClassNotFoundException l_e){
			l_class=Class.forName(defaultTheme+"."+p_name);
		}
		Class<?>[] l_types={Theme.class};
		Object[] l_params={this};
		
		return (ThemeItemBase)(l_class.getConstructor(l_types).newInstance(l_params));
	}
	
	
	public Theme(String p_baseName)
	{
		baseName=p_baseName;
		defaultTheme="org.elaya.page.defaultTheme";
	}
}
