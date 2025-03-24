package org.example.appointment.repository;

import org.example.appointment.entity.Doctor;
import org.example.appointment.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialty(Specialty specialty);
    List<Doctor> findByFullNameContainingIgnoreCase(String name);
    List<Doctor> findBySpecialtyAndFullNameContainingIgnoreCase(Specialty specialty, String name);
} 