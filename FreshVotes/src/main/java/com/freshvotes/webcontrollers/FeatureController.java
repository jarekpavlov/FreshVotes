package com.freshvotes.webcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freshvotes.domain.Feature;
import com.freshvotes.service.FeatureService;

@Controller
@RequestMapping("/product/{productId}/features")
public class FeatureController {

	@Autowired
	private FeatureService featureService;
	
	@PostMapping("")
	public String createFeatur(@PathVariable Long productId) {
		
		featureService.createFeature(productId);		
		return "features";
	}
	
	



}
