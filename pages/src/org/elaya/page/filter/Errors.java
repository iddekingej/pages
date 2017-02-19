package org.elaya.page.filter;

public class Errors {

	private Errors() {

	}
	
	public static class AuthenticationException extends Exception{
		private static final long serialVersionUID = -1421922930181558826L;
		
		public AuthenticationException(Throwable cause){
			super("Authentication exception",cause);
		}
	}
}
