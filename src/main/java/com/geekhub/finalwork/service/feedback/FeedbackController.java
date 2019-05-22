package com.geekhub.finalwork.service.feedback;

import com.geekhub.finalwork.authentication.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public String doGet(ModelMap modelMap, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelMap.put("firstName", user.getFirstName());
        modelMap.put("lastName", user.getLastName());
        List<Feedback> feedbacks = feedbackService.getFeedbackOrderedByDate();
        model.addAttribute("feedbacks", feedbacks);
        return "main/feedback.html";
    }

    @PostMapping
    public String doPost(@RequestParam String name, @RequestParam String text, @RequestParam int rank) {
        feedbackService.createFeedback(name, text, rank);
        return "redirect:/feedback";
    }
}