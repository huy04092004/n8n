package org.example.appointment.controller;

import jakarta.servlet.http.HttpSession;
import org.example.appointment.entity.Appointment;
import org.example.appointment.entity.User;
import org.example.appointment.service.AppointmentService;
import org.example.appointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                          @RequestParam String password,
                          Model model,
                          HttpSession session) {
        System.out.println("Nhận yêu cầu đăng nhập: " + email);

        Optional<User> userOpt = userService.getUserByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (userService.validatePassword(password, user.getPassword())) {
                System.out.println("Đăng nhập thành công! Chuyển đến /home");
                session.setAttribute("loggedInUser", user);
                return "redirect:/auth/home";
            }
        }

        System.out.println("Đăng nhập thất bại!");
        model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
        return "login";
    }

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/auth/login";
        }

        List<Appointment> appointments = appointmentService.getUserAppointments(loggedInUser);
        model.addAttribute("user", loggedInUser);
        model.addAttribute("appointments", appointments);

        return "home";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            return "redirect:/auth/login?success=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }

    @GetMapping("/profile")
    public String showProfile(@RequestParam Long userId, Model model) {
        userService.getUserById(userId).ifPresent(user -> model.addAttribute("user", user));
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/auth/profile?userId=" + user.getId() + "&success=true";
    }
}
