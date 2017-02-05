package org.elaya.page.widget.quickform;

import org.elaya.page.*;
import org.elaya.page.widget.Element;

public abstract class FormElement<T extends ThemeItemBase> extends Element<T> {
	
	public boolean hasValue(){ return true;}
	
}
