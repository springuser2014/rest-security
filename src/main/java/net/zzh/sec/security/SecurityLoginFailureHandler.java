/**
 * 
 */
package net.zzh.sec.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenhuazhao
 *
 */
@Service
@Component
//@Transactional
public class SecurityLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	// Spring Message Source
	//@Autowired
	//private MessageSource messageSource;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		System.out.println("SecurityLoginFailureHandler");
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			//MessageSource bean = ApplicationContextProvider.getContext().getBean(MessageSource.class);
			//String message = this.messageSource.getMessage("login.error", new Object [] {}, null);
			//response.getWriter().print("{\"state\": false， \"message\": \""+messageSource.getMessage("login.error", new Object [] {}, null)+"\"}");
			response.getWriter().flush();
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
