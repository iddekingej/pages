package org.elaya.page.filter;

import org.elaya.page.core.PageSession;
/**
 * Always disallow some action
 * Can be used to disallow to access to some url
 *
 */
public class DisallowedAction extends Action {

	@Override
	public ActionResult execute(PageSession session)	 {
		return ActionResult.NOTAUTHORISED;
	}

}
