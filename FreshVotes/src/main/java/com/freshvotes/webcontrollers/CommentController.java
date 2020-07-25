package com.freshvotes.webcontrollers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
			@RequestParam(required = false) Long parentId,Comment commentRoot, @RequestParam(required = false) String childCommentText){
	
		Optional<Feature> featureOpt=featureRepo.findById(featureId);
		
		if (StringUtils.isEmpty(commentRoot.getText())) {
			 Comment comment = new Comment();
			 Optional<Comment> parentComentOpt=commentRepo.findById(parentId);
			 if (parentComentOpt.isPresent())
				 comment.setComment(parentComentOpt.get());
			 comment.setText(childCommentText);
			 populatingAndSaveComment(comment,featureOpt ,user);
		}else if (parentId==null)
			populatingAndSaveComment(commentRoot,featureOpt ,user);
		
		
		return "redirect:/product/"+productId+"/features/"+featureId;
		
	}
	private Comment populatingAndSaveComment(Comment comment, Optional<Feature> featureOpt , User user) {
		if (featureOpt.isPresent())
			comment.setFeature(featureOpt.get());
		comment.setUser(user);
		comment.setCreatedDate(new Date());
		commentRepo.save(comment);
		
		
		return comment;
		
	}

}
