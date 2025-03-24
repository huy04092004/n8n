package org.example.appointment.repository;

import org.example.appointment.entity.Appointment;
import org.example.appointment.entity.Doctor;
import org.example.appointment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUser(User user);
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByDoctorAndAppointmentDateBetween(Doctor doctor, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByUserOrderByAppointmentDateDesc(User user);
} 