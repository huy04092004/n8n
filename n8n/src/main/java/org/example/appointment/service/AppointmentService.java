package org.example.appointment.service;

import org.example.appointment.entity.Appointment;
import org.example.appointment.entity.AppointmentStatus;
import org.example.appointment.entity.Doctor;
import org.example.appointment.entity.User;
import org.example.appointment.repository.AppointmentRepository;
import org.example.appointment.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Appointment> getUserAppointments(User user) {
        return appointmentRepository.findByUserOrderByAppointmentDateDesc(user);
    }

    public List<Appointment> getDoctorAppointments(Doctor doctor) {
        return appointmentRepository.findByDoctor(doctor);
    }

    public List<Appointment> getDoctorAppointmentsByDateRange(Doctor doctor, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByDoctorAndAppointmentDateBetween(doctor, start, end);
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointmentStatus(Long id, AppointmentStatus status) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    public void cancelAppointment(Long id) {
        updateAppointmentStatus(id, AppointmentStatus.CANCELLED);
    }

    public void completeAppointment(Long id) {
        updateAppointmentStatus(id, AppointmentStatus.COMPLETED);
    }

    public boolean isTimeSlotAvailable(Doctor doctor, LocalDateTime dateTime) {
        List<Appointment> appointments = getDoctorAppointmentsByDateRange(
            doctor,
            dateTime.minusHours(1),
            dateTime.plusHours(1)
        );
        return appointments.isEmpty();
    }
}
