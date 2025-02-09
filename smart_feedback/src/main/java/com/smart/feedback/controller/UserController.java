package com.smart.feedback.controller;

import java.util.Optional;
import java.util.Date;  // Import Date
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.smart.feedback.model.Feedback;
import com.smart.feedback.model.User;
import com.smart.feedback.repository.FeedbackRepository;
import com.smart.feedback.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        HttpSession session, Model model) {

        Optional<User> userOpt = userService.authenticate(username, password);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            session.setAttribute("user", user);

            String role = user.getRole();

            if (role == null || role.isEmpty()) {
                role = "APP_USER";
            }

            // If user is an admin, redirect to admin page, else to feedback page
            if (("APP_ADMIN").equals(role)) {
                return "redirect:/admin/feedback"; // Admin can see all feedbacks
            } else {
                model.addAttribute("feedbacks", feedbackRepository.findByUserId(user.getId()));
                return "redirect:/feedback"; // Regular user can only see their feedback
            }
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/feedback")
    public String feedbackPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("feedbacks", feedbackRepository.findByUserId(user.getId()));
        return "feedback";
    }

    @PostMapping("/feedback")
    public String submitFeedback(@RequestParam("feedback") String feedbackContent, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Feedback feedback = new Feedback();
        feedback.setFeedback(feedbackContent);
        feedback.setUserId(user.getId());
        feedback.setDate(new Date());  // Set current date when feedback is created

        feedbackRepository.save(feedback);

        return "redirect:/feedback";
    }

    @GetMapping("/admin/feedback")
    public String adminFeedbackPage(Model model) {
        model.addAttribute("feedbacks", feedbackRepository.findAll());
        return "adminFeedback";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam("username") String username, @RequestParam("password") String password,
                         @RequestParam("confirmPassword") String confirmPassword, Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "signup"; // Stay on the sign-up page
        }

        Optional<User> existingUser = userService.findByUsername(username);
        if (existingUser.isPresent()) {
            model.addAttribute("error", "Username is already taken.");
            return "signup"; // Stay on the sign-up page
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole("APP_USER");
        userService.saveNewUser(newUser);

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
