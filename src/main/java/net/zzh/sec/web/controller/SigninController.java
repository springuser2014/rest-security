/**
 * 
 */
package net.zzh.sec.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zzh.common.web.WebConstants;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhenhuazhao
 *
 */
@Controller
public class SigninController {

	/**
	 * 
	 */
	public SigninController() {
		super();
	}

	@RequestMapping(value = WebConstants.PATH_SIGNIN, method = RequestMethod.GET)
	public String signin(ModelMap model, HttpServletRequest request) {
		System.out.println(WebConstants.PATH_SIGNIN);
		
		String message = "This is a sign in page.";
		model.addAttribute("title", message);
		model.addAttribute("theme", message);
		model.addAttribute("msg", message);
		
		return "signin";
	}
	
	@RequestMapping(value = WebConstants.PATH_SIGNIN, method = RequestMethod.POST)
    @ResponseBody
	public Map signinCheck(ModelMap model, HttpServletRequest request) {
		System.out.println(WebConstants.PATH_SIGNIN + "-check");
		
		
		Map map = new HashMap();
		map.put("access", true);
		map.put("redirect", WebConstants.PATH_SEP);
		return map;
	}
}
