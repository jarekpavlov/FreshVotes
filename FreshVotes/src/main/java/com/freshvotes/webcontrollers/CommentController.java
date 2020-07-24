package com.freshvotes.webcontrollers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.CommentRepository;
import com.freshvotes.repositories.FeatureRepository;

@Controller
@RequestMapping("/product/{productId}/features/{featureId}/comments")
public class CommentController {
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private FeatureRepository featureRepo;
	
	@GetMapping("")
	@ResponseBody
	public List<Comment> getComments(@PathVariable Long featureId){
		
		return commentRepo.findByFeatureId(featureId);
	
	}
	
	@PostMapping("")
	public String saveComment(@AuthenticationPrincipal User user, @PathVariable Long featureId, @PathVariable Long productId,
			@RequestParam(required = false) Long parentId,Comment comment, @RequestParam(required = false) String childCommentText){
	
		Optional<Feature> featureOpt=featureRepo.findById(featureId);
		
		if (parentId!=null) {
			 comment = new Comment();

			 Optional<Comment> comentOpt=commentRepo.findById(parentId);
			 if (comentOpt.isPresent())
				 comment.setComment(comentOpt.get());
			 comment.setText(childCommentText);
		}
		
		if (featureOpt.isPresent())
			comment.setFeature(featureOpt.get());
		
		comment.setUser(user);
		comment.setCreatedDate(new Date());
		commentRepo.save(comment);
		
		
		return "redirect:/product/"+productId+"/features/"+featureId;
		
	}

}
