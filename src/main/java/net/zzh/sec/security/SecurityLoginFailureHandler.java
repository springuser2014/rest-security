/**
 * 
 */
package net.zzh.sec.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * @author zhenhuazhao
 *
 */
@Service
public class SecurityLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		//System.out.println("SecurityLoginFailureHandler");
		
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		Locale locale = RequestContextUtils.getLocale(request);
		
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			String message = ctx.getMessage("login.error", new Object[]{}, locale);

			response.setContentType("text/x-json; charset=utf-8");
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			response.getWriter().print("{\"state\": false, \"message\": \""+message+"\"}");
			response.getWriter().flush();
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
