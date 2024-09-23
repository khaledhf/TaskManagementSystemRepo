package com.example.taskmanagementsystem.controller;

import com.example.taskmanagementsystem.model.User;
import com.example.taskmanagementsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;



@Controller
@SessionAttributes({ "username" })
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }
    @PostMapping("/register")
    public String registerUser( @RequestParam String username ,
                                              @RequestParam String password) {
        userService.registerUser(username, password);
        return "login";
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            ModelMap modelMap, HttpSession session) {
        User user = userService.getUserByUsername(username);
        if (user != null && checkPassword(password, user.getPassword())) {
            session.setAttribute("username", user);
            return "redirect:/tasks";
        } else {
            modelMap.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/main";
    }
    private boolean checkPassword(String enteredPassword, String Password) {
        return enteredPassword.equals(Password);
    }
}
