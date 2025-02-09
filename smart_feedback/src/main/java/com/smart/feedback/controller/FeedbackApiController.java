package com.smart.feedback.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.feedback.model.Feedback;
import com.smart.feedback.repository.FeedbackRepository;

//Controller for handling feedback operations
@RestController
@RequestMapping("/api/feedback")
public class FeedbackApiController {

	@Autowired
	private FeedbackRepository feedbackRepository;

	// Update Feedback
	@PostMapping("/update/{id}")
	public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody Feedback updatedFeedback) {
		Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
		if (feedbackOptional.isPresent()) {
			Feedback feedback = feedbackOptional.get();
			feedback.setFeedback(updatedFeedback.getFeedback());
			feedbackRepository.save(feedback);
			return ResponseEntity.ok(feedback);
		}
		return ResponseEntity.notFound().build();
	}

	// Delete Feedback
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
		Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
		if (feedbackOptional.isPresent()) {
			feedbackRepository.deleteById(id);
			return ResponseEntity.noContent().build(); // 204 No Content
		}
		return ResponseEntity.notFound().build(); // 404 Not Found
	}
}
