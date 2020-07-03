package com.freshvotes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.FeatureRepository;
import com.freshvotes.repositories.ProductRepository;

@Service
public class FeatureService {

	@Autowired
	private FeatureRepository featurRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	public Feature createFeature(Long productId, User user) {
		
		Feature feature = new Feature();
		Optional<Product> productOpt=productRepo.findById(productId);
		
		if(productOpt.isPresent()) {
			
			Product product =productOpt.get();
			
			feature.setUser(user);
			user.getFeatures().add(feature);////I'm not sure is it necessary
			
			feature.setProduct(product);
			product.getFeatures().add(feature);////I'm not sure is it necessary
			
			feature.setStatus("Pending review");
			
			return featurRepo.save(feature);
		}
		return feature;
	}

	public Feature findById(Long featureId) {
		
		Optional<Feature> featureOpt=featurRepo.findById(featureId);
		Feature feature = new Feature();
		if (featureOpt.isPresent()) {
			 feature=featureOpt.get();
		}
		
		return feature;
	}

	public Feature save(Feature feature) {
		
		
		
		return featurRepo.save(feature);
		
		
	}

}
