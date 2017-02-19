package org.elaya.page.filter;

import org.elaya.page.core.PageSession;

public class DisallowedAction extends Action {

	@Override
	public ActionResult execute(PageSession session)	 {
		return ActionResult.NOTAUTHORISED;
	}

}
