package net.zzh.sec.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zzh.sec.model.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

@Controller
public class RootController {
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	public RootController() {
		super();
	}

	// API

	// search
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Principal findOne(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return new Principal();
    }

	@RequestMapping(value = "/hello", method = RequestMethod.GET)  
	public String login(ModelMap model) {
		System.out.println("/login");
		String message = "Welcome to Spring 4.0 !";
		model.addAttribute("title", message); 
		model.addAttribute("msg", message); 
		return "login";
	}
/*
	@RequestMapping(value = WebConstants.PATH_SEP, method = RequestMethod.GET)
	public ModelAndView test() {
		System.out.println("to all page - " + WebConstants.PATH_SEP);

		
		model.addAttribute("title", "This is a good day!");
		model.addAttribute("theme", "Skyrim");
		
		String message = "Welcome to Spring 4.0 !";
		return new ModelAndView("index", "title", message);
	}
	
*/
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
