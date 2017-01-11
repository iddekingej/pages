package org.elaya.page.security;

public class AllowedAction extends Action {

	@Override
	public ActionResult execute(Session session) 
	{
		return ActionResult.NEXTFILTER;
	}

}
