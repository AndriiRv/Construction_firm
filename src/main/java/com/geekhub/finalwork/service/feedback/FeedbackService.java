package com.geekhub.finalwork.service.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    void createFeedback(String name, String text, int rank) {
        feedbackRepository.createFeedback(name, text, rank);
    }

    List<Feedback> getFeedbackOrderedByDate() {
        return feedbackRepository.getFeedbackOrderedByDate();
    }
}