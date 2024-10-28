package com.example.Yieldstreeet.YieldstreetTask.service;

import com.example.Yieldstreeet.YieldstreetTask.entity.Accreditation;
import com.example.Yieldstreeet.YieldstreetTask.entity.Document;
import com.example.Yieldstreeet.YieldstreetTask.entity.User;
import com.example.Yieldstreeet.YieldstreetTask.repositories.AccreditationRepository;
import com.example.Yieldstreeet.YieldstreetTask.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccreditationService {

    @Autowired
    private AccreditationRepository accreditationRepository;
    @Autowired
    private UserRepository userRepository;

    public Accreditation createAccreditations(Long userId, String accreditationType, Document document) {

        User user = userRepository.findById(String.valueOf(userId)).orElseGet(() -> userRepository.save(new User(userId)));

        boolean alreadyPending = user.getAccreditations().stream().anyMatch(acc -> acc.getStatus().equals("PENDING"));

        if (alreadyPending) {
            throw new IllegalStateException("This Accreditation is already PENDING");
        }

        Accreditation accreditation = new Accreditation(accreditationType, document, user);
        return accreditationRepository.save(accreditation);
    }

    public Accreditation updateAccreditationStatus(UUID accreditationId, String outcome) {

        Accreditation accreditation = accreditationRepository.findById(accreditationId)
                .orElseThrow(() -> new IllegalArgumentException("Accreditation not found !"));

        if (accreditation.getStatus().equals("FAILED")) {
            throw new IllegalStateException("FAILED accreditation cannot be update");
        }

        accreditation.setStatus(outcome);
        accreditation.setLastUpdated(LocalDateTime.now());
        return accreditationRepository.save(accreditation);
    }

    public List<Accreditation> getAccreditationsByUserId(Long userId) {
        return accreditationRepository.findByUserUserId(userId);
    }
}
