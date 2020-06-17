package com.freshvotes.webcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
	
	//@RequestMapping(value="/login", method = RequestMethod.GET) =>
	@GetMapping(value = "/login")
	public String LogIn() {
		
		return "login";
	}
	@GetMapping(value = "/registration")
	public String Registration() {
		
		return "registration";
	}

}
