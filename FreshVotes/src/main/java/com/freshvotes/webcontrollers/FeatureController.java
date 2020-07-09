package com.freshvotes.webcontrollers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.service.FeatureService;

@Controller
@RequestMapping("/product/{productId}/features")
public class FeatureController {

	@Autowired
	private FeatureService featureService;
	
	@PostMapping("")
	public String createFeatur(@AuthenticationPrincipal User user, @PathVariable Long productId) {
		
		Feature feature=featureService.createFeature(productId,user);		
		return "redirect:/product/"+productId+"/features/"+feature.getId();
	}
	
	@GetMapping("{featureId}")
	public String mapFeatur(@AuthenticationPrincipal User user, ModelMap model, @PathVariable Long featureId,@PathVariable Long productId) {
		Feature feature = featureService.findById(featureId);
		model.put("comments", getCommentsWithotDuplicates(feature.getComments()));
		model.put("feature", feature);
		model.put("user",user);
		return "features";
	}
	
	public Set<Comment> getCommentsWithotDuplicates(Set<Comment> comments){
		
		return  comments;
	}
	
	@PostMapping("{featureId}")
	public String saveFeature(@AuthenticationPrincipal User user, Feature feature, @PathVariable Long featureId, @PathVariable Long productId) {
		
		feature.setUser(user);
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
