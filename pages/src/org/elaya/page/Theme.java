package org.elaya.page;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletResponse;

public class Theme {
	private String baseName;
	private String defaultTheme;
	private HttpServletResponse response;
	
	
	public ThemeItemBase getTheme(String p_name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Class<?> l_class;
		try{
			l_class=Class.forName(baseName+"."+p_name);
		}catch(ClassNotFoundException l_e){
			l_class=Class.forName(defaultTheme+"."+p_name);
		}
		Class<?>[] l_types={HttpServletResponse.class};
		Object[] l_params={response};
		
		return (ThemeItemBase)(l_class.getConstructor(l_types).newInstance(l_params));
	}
	
	
	public Theme(String p_baseName,HttpServletResponse p_response)
	{
		baseName=p_baseName;
		defaultTheme="org.elaya.page.defaultTheme";
		response=p_response;
	}
	
	public Theme(String p_baseName,String p_defaultTheme,HttpServletResponse p_response)
	{
		baseName=p_baseName;
		defaultTheme=p_defaultTheme;
		response=p_response;
	}
}
