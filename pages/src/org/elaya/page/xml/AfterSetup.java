package org.elaya.page.xml;

import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface AfterSetup {
	public void afterSetup() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
