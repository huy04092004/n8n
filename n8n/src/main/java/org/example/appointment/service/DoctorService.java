package org.example.appointment.service;

import org.example.appointment.entity.Doctor;
import org.example.appointment.entity.Specialty;
import org.example.appointment.repository.DoctorRepository;
import org.example.appointment.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public List<Doctor> getDoctorsBySpecialty(String specialtyName) {
        Optional<Specialty> specialty = specialtyRepository.findByName(specialtyName);
        return specialty.map(doctorRepository::findBySpecialty).orElse(List.of());
    }

    public List<Doctor> searchDoctors(String keyword) {
        return doctorRepository.findByFullNameContainingIgnoreCase(keyword);
    }

    public List<Doctor> searchDoctorsBySpecialtyAndName(String specialtyName, String keyword) {
        Optional<Specialty> specialty = specialtyRepository.findByName(specialtyName);
        return specialty.map(s -> doctorRepository.findBySpecialtyAndFullNameContainingIgnoreCase(s, keyword))
                       .orElse(List.of());
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
} 