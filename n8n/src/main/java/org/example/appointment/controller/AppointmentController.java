package org.example.appointment.controller;

import org.example.appointment.entity.Appointment;
import org.example.appointment.entity.AppointmentStatus;
import org.example.appointment.entity.Doctor;
import org.example.appointment.entity.User;
import org.example.appointment.service.AppointmentService;
import org.example.appointment.service.DoctorService;
import org.example.appointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listAppointments(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        userService.getUserByUsername(username).ifPresent(user -> {
            model.addAttribute("appointments", appointmentService.getUserAppointments(user));
        });
        return "appointments";
    }

    @GetMapping("/doctor/{doctorId}")
    public String showAppointmentForm(@PathVariable Long doctorId, Model model) {
        doctorService.getDoctorById(doctorId).ifPresent(doctor -> {
            model.addAttribute("doctor", doctor);
            model.addAttribute("appointment", new Appointment());
        });
        return "appointment-form";
    }

    @PostMapping
    public String createAppointment(@ModelAttribute Appointment appointment,
                                  @RequestParam Long doctorId,
                                  @RequestParam String appointmentDate,
                                  @RequestParam String appointmentTime) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        userService.getUserByUsername(username).ifPresent(user -> {
            doctorService.getDoctorById(doctorId).ifPresent(doctor -> {
                appointment.setUser(user);
                appointment.setDoctor(doctor);
                
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                
                LocalDateTime date = LocalDateTime.parse(appointmentDate + "T00:00", dateFormatter);
                LocalTime time = LocalTime.parse(appointmentTime, timeFormatter);
                
                appointment.setAppointmentDate(date);
                appointment.setAppointmentTime(time.toString());
                appointment.setStatus(AppointmentStatus.PENDING);
                
                appointmentService.createAppointment(appointment);
            });
        });
        
        return "redirect:/appointments?success=true";
    }

    @PostMapping("/{id}/cancel")
    public String cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return "redirect:/appointments?success=true";
    }

    @PostMapping("/{id}/complete")
    public String completeAppointment(@PathVariable Long id) {
        appointmentService.completeAppointment(id);
        return "redirect:/appointments?success=true";
    }

    @GetMapping("/doctor/{doctorId}/schedule")
    @ResponseBody
    public boolean checkTimeSlot(@PathVariable Long doctorId,
                               @RequestParam String dateTime) {
        return doctorService.getDoctorById(doctorId)
                .map(doctor -> appointmentService.isTimeSlotAvailable(doctor, LocalDateTime.parse(dateTime)))
                .orElse(false);
    }
}
