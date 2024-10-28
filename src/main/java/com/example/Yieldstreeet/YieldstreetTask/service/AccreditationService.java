package com.example.Yieldstreeet.YieldstreetTask.service;

import com.example.Yieldstreeet.YieldstreetTask.entity.Accreditation;
import com.example.Yieldstreeet.YieldstreetTask.entity.Document;
import com.example.Yieldstreeet.YieldstreetTask.entity.User;
import com.example.Yieldstreeet.YieldstreetTask.repositories.AccreditationRepository;
import com.example.Yieldstreeet.YieldstreetTask.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccreditationService {

    @Autowired
    private AccreditationRepository accreditationRepository;
    @Autowired
    private UserRepository userRepository;

    public Accreditation createAccreditation(Long userId, String accreditationType, Document document) {

        User user = userRepository.findById(String.valueOf(userId)).orElseGet(() -> userRepository.save(new User(userId)));

        boolean alreadyPending = user.getAccreditations().stream().anyMatch(acc -> acc.getStatus().equals("PENDING"));

        if (alreadyPending) {
            throw new IllegalStateException("This Accreditation is already PENDING");
        }

        Accreditation accreditation = new Accreditation(accreditationType, document, user);
        return accreditationRepository.save(accreditation);
    }
}
