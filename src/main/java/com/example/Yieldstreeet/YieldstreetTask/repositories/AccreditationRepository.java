package com.example.Yieldstreeet.YieldstreetTask.repositories;

import com.example.Yieldstreeet.YieldstreetTask.entity.Accreditation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AccreditationRepository extends JpaRepository<Accreditation, UUID> {
    List<Accreditation> findByUserUserId(Long userId);
    @Query("SELECT a FROM Accreditation a WHERE a.status = :status AND a.lastUpdated < :expiryDate")
    List<Accreditation> findByStatusAndLastUpdated(@Param("status") String status, @Param("expiryDate") LocalDateTime expiryDate);
}
