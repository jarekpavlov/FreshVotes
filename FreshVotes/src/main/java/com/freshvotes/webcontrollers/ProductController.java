package com.freshvotes.webcontrollers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	private Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductRepository productRepo;
	
	
	@GetMapping(value = "/p/{productName}")
	public String getProductView(@PathVariable String productName, ModelMap model) {
		if(productName!=null) {
			try {
					String decodedProductName=URLDecoder.decode(productName, StandardCharsets.UTF_8.name());
					Optional<Product> productOpt=productRepo.findByName(decodedProductName);
					if(productOpt.isPresent()) {
						Product product=productOpt.get();
						model.put("product", product);
				}
			} catch (UnsupportedEncodingException e) {
				log.error("There was an error decoding URL",e);
			}
		}
		return "productUserView";
	
	}

	
	@GetMapping(value = "/product/{productId}")
	public String getProductId(@PathVariable Long productId, ModelMap model, HttpServletResponse response ) throws IOException {
		
		 Optional<Product> ProductOpt = productRepo.findByIdWithUser(productId);
		 
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
