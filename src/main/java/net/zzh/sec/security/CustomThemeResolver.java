/**
 * 
 */
package net.zzh.sec.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zzh.common.web.WebConstants;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.theme.AbstractThemeResolver;
import org.springframework.web.servlet.theme.SessionThemeResolver;

/**
 * @author zhenhuazhao
 *
 */
@Configuration
public class CustomThemeResolver extends AbstractThemeResolver {

	private static String THEME_NAME = WebConstants.ORIGINAL_DEFAULT_THEME_NAME;
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.ThemeResolver#resolveThemeName(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String resolveThemeName(HttpServletRequest request) {
		//System.out.println("myThemeResolver: " + MyThemeResolver.THEME_NAME);
		return CustomThemeResolver.THEME_NAME;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.ThemeResolver#setThemeName(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	@Override
	public void setThemeName(HttpServletRequest request, HttpServletResponse response, String themeName) {
		if(themeName != null) {
			CustomThemeResolver.THEME_NAME = themeName;
		}
	}

}
