package org.example.appointment.service;

import org.example.appointment.mapper.AppointmentMapper;
import org.example.appointment.model.Appointment;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {
    private final AppointmentMapper appointmentMapper;
    private final N8nService n8nService; // Thêm N8nService

    public AppointmentService(AppointmentMapper appointmentMapper, N8nService n8nService) {
        this.appointmentMapper = appointmentMapper;
        this.n8nService = n8nService;
    }

//    public void bookAppointment(Appointment appointment) {
//        // Nếu ngày hẹn bị null, tự động lấy ngày hôm nay
//        if (appointment.getAppointmentDate() == null) {
//            appointment.setAppointmentDate(LocalDate.now());
//        }
//        if (appointment.getAppointmentTime() == null) {
//            appointment.setAppointmentTime(LocalTime.now());
//        }
//        if (appointment.getStatus() == null) {
//            appointment.setStatus("pending"); // Đặt mặc định
//        }
//
//        System.out.println("Đang lưu lịch hẹn: " + appointment);
//        appointmentMapper.insertAppointment(appointment);
//
//        // Gửi webhook đến n8n sau khi lưu thành công
//        n8nService.sendAppointmentToN8n(appointment);
//    }
public void bookAppointment(Appointment appointment) {
    if (appointment.getAppointmentDate() == null) {
        appointment.setAppointmentDate(LocalDate.now());
    }
    if (appointment.getAppointmentTime() == null) {
        appointment.setAppointmentTime(LocalTime.now());
    }
    if (appointment.getStatus() == null) {
        appointment.setStatus("pending");
    }

    // Sinh token hủy lịch hẹn
    appointment.setCancellationToken(UUID.randomUUID().toString());

    System.out.println("Đang lưu lịch hẹn: " + appointment);
    appointmentMapper.insertAppointment(appointment);

    // Gửi webhook đến n8n
    n8nService.sendAppointmentToN8n(appointment);
}

    public List<Appointment> getAppointmentsByUser(int userId) {
        return appointmentMapper.getAppointmentsByUser(userId);
    }
    public boolean cancelAppointmentByToken(String token) {
        Appointment appointment = appointmentMapper.getAppointmentByToken(token);
        if (appointment != null && !"cancelled".equals(appointment.getStatus())) {
            appointment.setStatus("cancelled");
            appointmentMapper.updateAppointment(appointment);

            // Gửi webhook đến n8n để thông báo hủy lịch hẹn
            n8nService.sendCancellationToN8n(appointment);

            return true;
        }
        return false;
    }

}
