package com.freshvotes.webcontrollers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;

@Controller
public class ProductController {
	
	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping(value = "/product")
	public String getProduct() {
		return "product";
		
	}
	@GetMapping(value = "/product/{productId}")
	public String getProductId(@PathVariable Long productId, ModelMap model, HttpServletResponse response ) throws IOException {
		
		 Optional<Product> ProductOpt = productRepo.findById(productId);
		 
		 if (ProductOpt.isPresent()) {
			 Product product =ProductOpt.get();
			 model.put("product", product);
		 }else {
			 response.sendError(HttpStatus.NOT_FOUND.value(), "Product with id "+productId+" is not found");
		 }
		
		return "product";
	
	}
	
	@PostMapping(value = "/product")
	public String createProduct(@AuthenticationPrincipal User user) { 
		
		Product product = new Product();
		product.setUser(user);
		product.setPublished(false);
		product = productRepo.save(product);
		return "redirect:/product/"+product.getId();
	}
	
	@PostMapping(value = "/product/{productId}")
	public String saveProduct(@PathVariable Long productId, Product product) {
		
		product = productRepo.save(product);
		return "redirect:/product/"+product.getId(); 
		

	}

}
