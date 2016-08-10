package org.elaya.page.form;

public class CheckboxElement extends BuildInFormElement {

	public CheckboxElement()
	{
		super();
	}


	@Override
	public void  display(String p_value) throws Exception {
		themeItem.checkBoxElement(getName(),p_value);

	}


	@Override
	public String getJsType() {
		return "checkbox";
	}

}
