package com.example.Yieldstreeet.YieldstreetTask.service;

import com.example.Yieldstreeet.YieldstreetTask.entity.Accreditation;
import com.example.Yieldstreeet.YieldstreetTask.repositories.AccreditationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AccreditationScheduler {

    @Autowired
    private AccreditationRepository accreditationRepository;


    @Scheduled(cron = "0 0 6 * * ?") //This cronjob will run every day at 06:00AM (Comment the whole line to test the cronjob to run immediately)
    @Transactional
    public void expiredAccreditations() {
        LocalDateTime expiry = LocalDateTime.now().minusDays(30);
        List<Accreditation> accreditationExpired = accreditationRepository.findByStatusAndLastUpdated("CONFIRMED", expiry);

        accreditationExpired.forEach(accreditation -> accreditation.setStatus("EXPIRED"));
        accreditationRepository.saveAll(accreditationExpired);

        System.out.println("Manual cron job executed: Expired " + accreditationExpired.size() + " items.");
    }
}
