package org.elaya.page.receiver;

import org.elaya.page.core.DynamicMethod;
import org.json.JSONException;

public abstract class Validator extends DynamicMethod {

	public Validator() {
	}

	abstract void validate(Result result,ReceiverData data) throws DynamicException, JSONException;
}
