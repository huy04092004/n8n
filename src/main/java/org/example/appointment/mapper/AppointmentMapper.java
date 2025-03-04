package org.example.appointment.mapper;

import org.apache.ibatis.annotations.*;
import org.example.appointment.model.Appointment;
import java.util.List;
@Mapper
public interface AppointmentMapper {
    @Insert("INSERT INTO appointments (user_id, appointment_date, appointment_time, status, notes) " +
            "VALUES (#{userId}, #{appointmentDate, jdbcType=DATE}, #{appointmentTime, jdbcType=TIME}, #{status}, #{notes})")
    void insertAppointment(Appointment appointment);

    @Select("SELECT * FROM appointments WHERE user_id = #{userId} ORDER BY appointment_date DESC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "appointmentDate", column = "appointment_date"),
            @Result(property = "appointmentTime", column = "appointment_time"),
            @Result(property = "status", column = "status"),  // Ánh xạ cột status
            @Result(property = "notes", column = "notes")
    })
    List<Appointment> getAppointmentsByUser(int userId);
}
