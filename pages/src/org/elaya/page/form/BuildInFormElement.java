package org.elaya.page.form;

import org.elaya.page.Element;
import org.elaya.page.form.FormExceptions.ElementNoSub;
import org.elaya.page.form.FormExceptions.InvalidElement;

public abstract class BuildInFormElement extends FormElement<FormElementThemeItem> {

	public BuildInFormElement()
	{
		super();
	}
	
	@Override
	final public String getThemeName() {
		return "FormElementThemeItem";
	}

	public void checkElement(Element<?> p_element) throws InvalidElement, ElementNoSub
	{
		throw new FormExceptions.ElementNoSub(this.getClass().getName());
	}
	

}
