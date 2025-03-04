package org.example.appointment.service;

import org.example.appointment.mapper.AppointmentMapper;
import org.example.appointment.model.Appointment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(AppointmentMapper appointmentMapper) {
        this.appointmentMapper = appointmentMapper;
    }

    public void bookAppointment(Appointment appointment) {
        // Nếu ngày hẹn bị null, tự động lấy ngày hôm nay
        if (appointment.getAppointmentDate() == null) {
            appointment.setAppointmentDate(LocalDate.now());
        }
        if (appointment.getAppointmentTime() == null) {
            appointment.setAppointmentTime(LocalTime.now());
        }

        System.out.println("Đang lưu lịch hẹn: " + appointment);
        appointmentMapper.insertAppointment(appointment);
    }
    public List<Appointment> getAppointmentsByUser(int userId) {
        return appointmentMapper.getAppointmentsByUser(userId);
    }
}
