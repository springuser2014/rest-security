/**
 * 
 */
package net.zzh.sec.security;

import javax.servlet.http.Cookie;

import net.zzh.common.web.WebConstants;

/**
 * @author zhenhuazhao
 *
 */
public interface CookieService {
	
	static final String COOKIE_NAME = "AUTHCOOKIE";

	public String extractUserName(Cookie[] cookies);

	public Cookie getCookie(String cookieName, Cookie[] cookies);

	public Cookie createCookie(String userName);

	public static class Impl implements CookieService {

		@Override
		public String extractUserName(Cookie[] cookies) {
			Cookie cookie = getCookie(COOKIE_NAME, cookies);
			return cookie == null ? null : cookie.getValue();
		}

		@Override
		public Cookie createCookie(String userName) {
			Cookie c = new Cookie(COOKIE_NAME, userName);
			c.setPath(WebConstants.PATH_SEP);

			return c;
		}

		@Override
		public Cookie getCookie(String cookieName, Cookie[] cookies) {
			if (cookies == null) {
				return null;
			}

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie;
				}
			}

			return null;
		}
	}
}
