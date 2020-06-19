package com.freshvotes.webcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.freshvotes.domain.User;
import com.freshvotes.service.UserService;


@Controller
public class LoginController {
	
	@Autowired
	UserService userService;
	
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
		userService.save(user);
		return "redirect:/login";
	}

}
