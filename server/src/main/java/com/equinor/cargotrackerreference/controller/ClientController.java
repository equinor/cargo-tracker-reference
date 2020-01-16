package com.equinor.cargotrackerreference.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientController {

	/*
	 * Make sure sub-urls are redirected to the index (single page app)
	 */
	@RequestMapping({ "/capture", "/capture/**", "/maintain/**", "/callback", "/callback/**", "/analytics", "/analytics/**", "/wetlist", "/wetlist**" })
	public String index() {
		return "forward:/index.html";
	}

}
