/**
 * 
 */
package net.zzh.ui.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zzh.ui.web.common.CommonResponseJSONBean;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 动态切换主题
 * @author zhenhuazhao
 * 
 */
@Controller
@RequestMapping(value = "/changetheme")
public class ThemeChangeController {

	@RequestMapping(value = "/{theme}")
	@ResponseBody
	public CommonResponseJSONBean changeTheme(@PathVariable("theme") final String themeName,
			final UriComponentsBuilder uriBuilder,
			final HttpServletResponse response,
			final HttpServletRequest request) {
		ThemeResolver themeResolver = RequestContextUtils.getThemeResolver(request);
		themeResolver.setThemeName(request, response, themeName);
		
		CommonResponseJSONBean crJson = new CommonResponseJSONBean();
		crJson.setRc(true);
		return crJson;
	}
}
