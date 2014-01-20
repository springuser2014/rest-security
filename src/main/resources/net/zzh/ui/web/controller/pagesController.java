/**
 * 
 */
package net.zzh.ui.web.controller;

import javax.servlet.http.HttpServletRequest;

import net.zzh.common.web.WebConstants;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhenhuazhao
 *
 */
@Controller
@RequestMapping(value = "/pages/")
public class pagesController {

@RequestMapping(value = "/1", method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request) {
		System.out.println("/pages/");
		String message = "Welcome to Spring 4.0 !";
		String url = request.getRequestURL().toString(),
				uri = request.getRequestURI(),
				path = request.getContextPath();
		
		
		model.addAttribute("path", url.replace(uri, path)); 
		model.addAttribute("title", message); 
		model.addAttribute("theme", message); 
		return "/pages/index";
	}
}
