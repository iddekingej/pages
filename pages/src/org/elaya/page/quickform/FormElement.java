package org.elaya.page.quickform;

import org.elaya.page.*;

public abstract class FormElement<T extends ThemeItemBase> extends Element<T> {
	
	public boolean hasValue(){ return true;}
	
}
