package com.example.Yieldstreeet.YieldstreetTask.repositories;

import com.example.Yieldstreeet.YieldstreetTask.entity.Accreditation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AccreditationRepository extends JpaRepository<Accreditation, UUID> {
    List<Accreditation> findByUserId(String userId);
    List<Accreditation> findByStatusAndLastUpdatedBefore(String status, LocalDateTime expiryDate);
}
