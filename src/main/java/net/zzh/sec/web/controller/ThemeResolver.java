/**
 * 
 */
package net.zzh.sec.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.theme.AbstractThemeResolver;

/**
 * @author zzh
 * 主题
 */
public class ThemeResolver extends AbstractThemeResolver {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.ThemeResolver#resolveThemeName(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String resolveThemeName(HttpServletRequest arg0) {
		System.out.println("ThemeResolver.");
		return "default";
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.ThemeResolver#setThemeName(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	@Override
	public void setThemeName(HttpServletRequest arg0, HttpServletResponse arg1,
			String arg2) {
		// TODO Auto-generated method stub

	}

}
