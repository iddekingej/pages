package org.elaya.page.filter;

import org.elaya.page.core.PageSession;

/**
 * This object indicates that some action is allowed
 * (For example access a certain url)
 */
public class AllowedAction extends Action {

	@Override
	public ActionResult execute(PageSession session) 
	{
		return ActionResult.NEXTFILTER;
	}

}
