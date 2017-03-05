package org.elaya.page.filter;

import java.util.LinkedList;
import javax.servlet.ServletRequest;
import org.elaya.page.core.AuthorizationData;
import org.elaya.page.core.PageSession;
/**
 * A filter consist of a RequestMatchter and a action.
 * The RequestMatchter defines when the action is executed.
 *
 */

public abstract class RequestMatcher implements HasRequestMatchers{
	/**
	 * List of actions to execute when a request matches
	 */
	private LinkedList<Action> actions=new  LinkedList<>();
	/**
	 * List of sub matchers.
	 * 
	 * When this object matches, the fist sub matcher is searched that also matches
	 * When one is found the action inside this matcher is executed.
	 * When no sub matcher that match is found all action inside this object is executed
	 * for example:
	 * Matcher1
	 *    -Sub matcher 1
	 *      -Action S1
	 *    -Sub matcher 2
	 *      -Action S2
	 *    - Action 1 (belonging to matcher 1)
	 *    
	 * Action S2 is executed when matchter1 matches and also sub matcher 2 matches
	 * When no sub matcher matches 'Action 1" is executed.
	 */
	private LinkedList<RequestMatcher> subMatcher=new LinkedList<>(); 

	protected AuthorizationData getSessionFromRequest(ServletRequest prequest)
	{
		Object object=prequest.getAttribute("org.elaya.page.security.SessionData");
		if(object !=null && object instanceof AuthorizationData){
			return (AuthorizationData)object;
		}

		return null;
	}
	
	/**
	 * Find the matcher or one of it's sub matchers that matches the request
	 * 
	 * @param session The matcher is match to the request inside this session
	 * @return The matched matcher or nulll when nothing found.
	 */
	public final RequestMatcher matchRequest(PageSession session){
		if(matchOwnRequest(session)){
			RequestMatcher found;
			for(RequestMatcher sub:subMatcher){
				found=sub.matchRequest(session);
				if(found != null){
					return found;
				}
			}
			return this;
		} else {
			return null;
		}
	}
	abstract boolean matchOwnRequest(PageSession session);
	
	
	/**
	 * Execute all actions inside this matcher.
	 * 
	 * @param session
	 * @return
	 * 
	 */
	public MatchActionResult execute(PageSession session) throws ActionException {
		ActionResult result;
		boolean nextFilter=true;
		for(Action action:actions){
			result=action.execute(session);
			if(result==ActionResult.SECURITYFAILED){
				return MatchActionResult.SECURITYFAILED;
			}
			if(result==ActionResult.NOTAUTHORISED){
				return MatchActionResult.NOTAUTHORIZED;
			}
			if(result==ActionResult.STOPACTIONS){
				break;
			}
			if(result==ActionResult.NONEXTFILTER){
				nextFilter=false;
			}
		}
		return nextFilter?MatchActionResult.NEXTFILTER:MatchActionResult.NONEXTFILTER;
	}
	
	/**
	 * Add an action to the matcher
	 * 
	 * @param paction Action to add
	 */
	public void addAction(Action paction){
		actions.add(paction);
	}
	
	@Override
	public void addRequestMatcher(RequestMatcher prequestMatcher){
		subMatcher.add(prequestMatcher);
	}
}
