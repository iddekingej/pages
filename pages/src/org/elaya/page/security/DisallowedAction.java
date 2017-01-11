package org.elaya.page.security;

public class DisallowedAction extends Action {

	@Override
	public ActionResult execute(Session session)	 {
		return ActionResult.NOTAUTHORISED;
	}

}
