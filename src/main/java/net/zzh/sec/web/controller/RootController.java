package net.zzh.sec.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zzh.common.web.WebConstants;
import net.zzh.sec.model.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@RequestMapping(value = WebConstants.PATH_SEP, method = RequestMethod.GET)  
	public String index(ModelMap model, HttpServletRequest request) {
		System.out.println(WebConstants.PATH_SEP);
		String ua=request.getHeader("User-Agent").toLowerCase();
		boolean isMobile = false;
		if(ua.matches("(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino).*")||ua.substring(0,4).matches("(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-")) {
			isMobile = true;
		}
		
		String message = "Welcome to Spring 4.0 !";
		model.addAttribute("title", message);
		model.addAttribute("theme", message);
		model.addAttribute("mobile", isMobile);
		
		return "index";
	}
	
	/**
	 * 默认返回xml，如果返回类型为Map或List则返回json
	 * @param id
	 * @param uriBuilder
	 * @param response
	 * @return xml
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Principal findOne(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return new Principal();
    }

	@RequestMapping(value = "/hello", method = RequestMethod.GET)  
	public String login(ModelMap model) {
		System.out.println("/signin");
		String message = "Welcome to Spring 4.0 !";
		model.addAttribute("title", message); 
		model.addAttribute("msg", message); 
		return "signin";
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
