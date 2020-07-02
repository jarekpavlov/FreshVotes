package com.freshvotes.webcontrollers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
		Feature feature=featureService.createFeature(productId);		
		return "redirect:/product/"+productId+"/features/"+feature.getId();
	}
	
	@GetMapping("{featureId}")
	public String mapFeatur(ModelMap model, @PathVariable Long featureId,@PathVariable Long productId) {
		Feature feature = featureService.findById(featureId);
		model.put("feature", feature);
		return "features";
	}
	
	@PostMapping("{featureId}")
	public String saveFeature(Feature feature, @PathVariable Long featureId, @PathVariable Long productId) {
		
		String encodedProductName;
		
		try {
			encodedProductName = URLEncoder.encode(feature.getProduct().getName(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "redirect:/dashboard";
		}
		featureService.save(feature);
		
		return "redirect:/p/"+encodedProductName;
	}
	



}
