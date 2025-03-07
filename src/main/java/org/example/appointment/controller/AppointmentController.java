package org.example.appointment.controller;

import org.example.appointment.model.Appointment;
import org.example.appointment.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @GetMapping("/cancel/{token}")
    public ResponseEntity<String> cancelAppointment(@PathVariable String token) {
        boolean isCancelled = appointmentService.cancelAppointmentByToken(token);
        if (isCancelled) {
            return ResponseEntity.ok("Lịch hẹn của bạn đã được hủy thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token không hợp lệ hoặc lịch hẹn đã bị hủy trước đó.");
        }
    }
    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.bookAppointment(appointment);
        return "redirect:/auth/home"; // Sau khi đặt lịch, quay về trang home
    }
}
