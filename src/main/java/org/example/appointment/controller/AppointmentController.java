package org.example.appointment.controller;

import org.example.appointment.model.Appointment;
import org.example.appointment.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.bookAppointment(appointment);
        return "redirect:/auth/home"; // Sau khi đặt lịch, quay về trang home
    }
}
