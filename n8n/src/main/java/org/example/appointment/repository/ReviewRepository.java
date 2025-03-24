package org.example.appointment.repository;

import org.example.appointment.entity.Doctor;
import org.example.appointment.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByDoctor(Doctor doctor);
    List<Review> findByDoctorOrderByCreatedAtDesc(Doctor doctor);
} 