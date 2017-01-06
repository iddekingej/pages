package org.elaya.page.receiver;

import org.elaya.page.data.Dynamic;
import org.elaya.page.data.DynamicMethod;
import org.json.JSONException;

public abstract class Validator<T extends Dynamic> extends DynamicMethod {

	public Validator() {
	}

	abstract void validate(Result result,ReceiverData<T> data) throws DynamicException, JSONException;
}
