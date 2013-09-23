package net.zzh.sec.web.controller;

import javax.servlet.http.HttpServletResponse;

import net.zzh.common.util.LinkUtil;
import net.zzh.common.web.WebConstants;
import net.zzh.sec.web.common.UriMappingConstants;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.net.HttpHeaders;

@Controller
public class RootController {

	public RootController() {
		super();
	}

	// API

	// search

	@RequestMapping(value = WebConstants.PATH_SEP, method = RequestMethod.GET)
	public String loadView(String viewName, Model model) {
		System.out.println("to all page - " + viewName);

		model.addAttribute("title", "This is a good day!");
		model.addAttribute("theme", "Skyrim");

		return "login";
	}
	/*
	@RequestMapping(value = WebConstants.PATH_SEP, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void rootWithPathSeparator(final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
		System.out.println("rootWithPathSeparator");
		rootInternal(uriBuilder, response);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void rootBare(final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
		System.out.println("rootBare");
		rootInternal(uriBuilder, response);
	}

	@SuppressWarnings("unused")
	private void rootInternal(final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
		final String userUriNew = uriBuilder.path("/" + UriMappingConstants.USERS).build().toUriString();

		final String userUri = LinkUtil.createLinkHeader(WebConstants.PATH_SEP + UriMappingConstants.USERS, LinkUtil.REL_COLLECTION);
		final String roleUri = LinkUtil.createLinkHeader(WebConstants.PATH_SEP + UriMappingConstants.ROLES, LinkUtil.REL_COLLECTION);
		final String privilegeUri = LinkUtil.createLinkHeader(WebConstants.PATH_SEP + UriMappingConstants.PRIVILEGES, LinkUtil.REL_COLLECTION);
		final String discoveredOperations = LinkUtil.gatherLinkHeaders(userUri, roleUri, privilegeUri);
		response.addHeader(HttpHeaders.LINK, discoveredOperations);

	}
*/
}
