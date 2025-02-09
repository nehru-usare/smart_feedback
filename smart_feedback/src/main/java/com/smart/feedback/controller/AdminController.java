package com.smart.feedback.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.smart.feedback.model.Feedback;
import com.smart.feedback.repository.FeedbackRepository;

@Controller
public class AdminController {

	@Autowired
	private FeedbackRepository feedbackRepository;

	private RestTemplate restTemplate = new RestTemplate();

	private static final String BASE_API_URL = "http://localhost:8080/api/feedback"; // Adjust the base API URL
																						// accordingly

	@GetMapping("/admin/viewFeedback/{id}")
	public String viewFeedback(@PathVariable("id") Long id, Model model) {
		Optional<Feedback> feedback = feedbackRepository.findById(id);
		if (feedback.isPresent()) {
			model.addAttribute("feedback", feedback.get());
			return "viewFeedback";
		}
		return "redirect:/admin/feedback";
	}

	@GetMapping("/admin/editFeedback/{id}")
	public String editFeedback(@PathVariable("id") Long id, Model model) {
		Optional<Feedback> feedback = feedbackRepository.findById(id);
		if (feedback.isPresent()) {
			model.addAttribute("feedback", feedback.get());
			return "editFeedback";
		}
		return "redirect:/admin/feedback";
	}

	@PostMapping("/admin/editFeedback/{id}")
	public String updateFeedback(@PathVariable("id") Long id, @RequestParam("feedback") String updatedFeedback) {
		Feedback updatedFeedbackObj = new Feedback();
		updatedFeedbackObj.setId(id);
		updatedFeedbackObj.setFeedback(updatedFeedback);

		String apiUrl = BASE_API_URL + "/update/" + id;
		restTemplate.postForObject(apiUrl, updatedFeedbackObj, Feedback.class);

		return "redirect:/admin/feedback";
	}

	@GetMapping("/admin/deleteFeedback/{id}")
	public String deleteFeedback(@PathVariable("id") Long id) {
		String apiUrl = BASE_API_URL + "/delete/" + id;
		restTemplate.delete(apiUrl);

		return "redirect:/admin/feedback";
	}
}
