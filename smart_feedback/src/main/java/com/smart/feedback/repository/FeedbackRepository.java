package com.smart.feedback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.feedback.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	List<Feedback> findByUserId(Long userId);
}
