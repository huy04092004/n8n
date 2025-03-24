package org.example.appointment.controller;

import org.example.appointment.entity.Doctor;
import org.example.appointment.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors";
    }

    @GetMapping("/{id}")
    public String showDoctorDetail(@PathVariable Long id, Model model) {
        doctorService.getDoctorById(id).ifPresent(doctor -> model.addAttribute("doctor", doctor));
        return "doctor-detail";
    }

    @GetMapping("/search")
    public String searchDoctors(@RequestParam(required = false) String specialty,
                               @RequestParam(required = false) String keyword,
                               Model model) {
        if (specialty != null && keyword != null) {
            model.addAttribute("doctors", doctorService.searchDoctorsBySpecialtyAndName(specialty, keyword));
        } else if (specialty != null) {
            model.addAttribute("doctors", doctorService.getDoctorsBySpecialty(specialty));
        } else if (keyword != null) {
            model.addAttribute("doctors", doctorService.searchDoctors(keyword));
        } else {
            model.addAttribute("doctors", doctorService.getAllDoctors());
        }
        return "doctors";
    }

    @GetMapping("/admin")
    public String adminDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "admin/doctors";
    }

    @PostMapping("/admin")
    public String createDoctor(@ModelAttribute Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return "redirect:/doctors/admin?success=true";
    }

    @PutMapping("/admin/{id}")
    public String updateDoctor(@PathVariable Long id, @ModelAttribute Doctor doctor) {
        doctor.setId(id);
        doctorService.saveDoctor(doctor);
        return "redirect:/doctors/admin?success=true";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors/admin?success=true";
    }
} 