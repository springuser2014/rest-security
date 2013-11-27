/**
 * 
 */
package net.zzh.sec.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author zhenhuazhao
 *
 */
@Service
public class SecurityLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	public SecurityLoginSuccessHandler() {
		super();
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		System.out.println("SecurityLoginSuccessHandler");
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			System.out.println(request.getHeader("X-Requested-With"));
			response.getWriter().print("{success:true, targetUrl : \'" + this.getTargetUrlParameter() + "\'}");
			response.getWriter().flush();
		} else {
			System.out.println("none");
			super.onAuthenticationSuccess(request, response, auth);
		}
	}

}
