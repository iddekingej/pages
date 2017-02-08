package org.elaya.page.security;

import org.elaya.page.core.PageSession;

public class AllowedAction extends Action {

	@Override
	public ActionResult execute(PageSession session) 
	{
		return ActionResult.NEXTFILTER;
	}

}
