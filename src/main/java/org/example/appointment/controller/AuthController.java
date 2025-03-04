package org.example.appointment.controller;

import jakarta.servlet.http.HttpSession;
import org.example.appointment.model.Appointment;
import org.example.appointment.model.User;
import org.example.appointment.service.AppointmentService;
import org.example.appointment.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AppointmentService appointmentService; // Thêm AppointmentService

    public AuthController(UserService userService, AppointmentService appointmentService) {
        this.userService = userService;
        this.appointmentService = appointmentService; // Gán vào constructor
    }
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Trả về login.html
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model,
                            HttpSession session) {
        System.out.println("Nhận yêu cầu đăng nhập: " + email);

        Optional<User> user = userService.loginUser(email, password);
        if (user.isPresent()) {
            System.out.println(" Đăng nhập thành công! Chuyển đến /home");
            session.setAttribute("loggedInUser", user.get()); // Lưu user vào session
            return "redirect:/auth/home";
        }

        System.out.println("Đăng nhập thất bại!");
        model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
        return "login";
    }
//    @GetMapping("/home")
//    public String homePage(HttpSession session, Model model) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null) {
//            return "redirect:/auth/login"; // Nếu chưa đăng nhập, quay về login
//        }
//
//        model.addAttribute("user", loggedInUser);
//        model.addAttribute("appointments", appointmentService.getAppointmentsByUser(loggedInUser.getId()));
//        return "home"; // Trả về trang home.html
//    }
@GetMapping("/home")
public String homePage(HttpSession session, Model model) {
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        return "redirect:/auth/login"; // Nếu chưa đăng nhập, chuyển về login
    }

    // Lấy danh sách lịch khám từ AppointmentService
    List<Appointment> appointments = appointmentService.getAppointmentsByUser(loggedInUser.getId());

    // Truyền danh sách lịch khám vào model
    model.addAttribute("user", loggedInUser);
    model.addAttribute("appointments", appointments);

    return "home";
}


    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.registerUser(user)) {
            return "redirect:/auth/login"; // Thành công -> chuyển đến login
        } else {
            model.addAttribute("error", "Email đã tồn tại!");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa session khi logout
        return "redirect:/auth/login";
    }
}
