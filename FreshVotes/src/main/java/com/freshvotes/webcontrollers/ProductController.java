package com.freshvotes.webcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;

@Controller
public class ProductController {
	
	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping(value = "/product")
	public String getProduct(ModelMap map) {
		return "product";
	}
	@GetMapping(value = "/product/{product}")
	public String getProductId(ModelMap map) {
		return "product";
	}
	
	@PostMapping(value = "/product")
	public String createProduct(@AuthenticationPrincipal User user) { 
		
		Product product = new Product();
		product.setUser(user);
		product.setPublished(false);
		productRepo.save(product);
		return "redirect:/product/"+user.getId();
	}

}
