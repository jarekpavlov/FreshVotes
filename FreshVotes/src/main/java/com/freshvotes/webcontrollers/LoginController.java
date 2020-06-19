package com.freshvotes.webcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.freshvotes.domain.User;


@Controller
public class LoginController {
	
	//@RequestMapping(value="/login", method = RequestMethod.GET) =>
	@GetMapping(value = "/login")
	public String LogIn() {
		
		return "login";
	}
	
	@GetMapping(value = "/registration")
	public String Registration(ModelMap model) {
		model.put("user", new User());
		return "registration";
	}
	
	@PostMapping(value = "/registration")
	public String createAccount(User user) {
		
		System.out.println(user);
		
		return "redirect:/registration";
	}

}
