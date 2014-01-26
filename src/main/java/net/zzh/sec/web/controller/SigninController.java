/**
 * 
 */
package net.zzh.sec.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zzh.common.web.WebConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhenhuazhao
 *
 */
@Controller
@RequestMapping(value = WebConstants.PATH_SIGNIN)
public class SigninController {

	@Autowired
	private MessageSource messageSource;
	/**
	 * 
	 */
	public SigninController() {
		super();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String signin(@RequestParam(required=false) String login_error, ModelMap model, HttpServletRequest request) {
		System.out.println(WebConstants.PATH_SIGNIN);
		
		String message = "This is a sign in page.";
		model.addAttribute("title", messageSource.getMessage("message.header", new Object [] {}, null));
		model.addAttribute("theme", message);
		model.addAttribute("msg", messageSource.getMessage("message.welcomeback", new Object [] {"Username"}, null));
		
		return "signin";
	}
	
	@RequestMapping(method = RequestMethod.POST)
    @ResponseBody
	public Map signinCheck(ModelMap model, HttpServletRequest request) {
		System.out.println(WebConstants.PATH_SIGNIN + "-check");
		
		String username = request.getParameter("u");
		String password = request.getParameter("p");

		System.out.println("username: "+ username + ", " + "password: " + password);
		
		Map map = new HashMap();
		map.put("access", true);
		map.put("redirect", WebConstants.PATH_SEP);
		return map;
	}
}
