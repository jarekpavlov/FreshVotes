package com.freshvotes.webcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;


@Controller
public class DashBoardController {
	
	@Autowired
	private ProductRepository productRepo;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String rootView() {
		return "index";
	}
	@GetMapping(value = "/dashboard")
	public String DashBoardView(@AuthenticationPrincipal User user, ModelMap model) {
		
		List<Product> products = productRepo.findByUser(user);
		model.put("products", products);
		return "dashboard";
	
	}

}
