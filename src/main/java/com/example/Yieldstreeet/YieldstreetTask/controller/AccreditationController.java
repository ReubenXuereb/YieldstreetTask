package com.example.Yieldstreeet.YieldstreetTask.controller;

import com.example.Yieldstreeet.YieldstreetTask.entity.Accreditation;
import com.example.Yieldstreeet.YieldstreetTask.entity.User;
import com.example.Yieldstreeet.YieldstreetTask.entity.Document;
import com.example.Yieldstreeet.YieldstreetTask.service.AccreditationService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class AccreditationController {

    @Autowired
    private AccreditationService accreditationService;

    @PostMapping("/{userId}/accreditation")
    public Map<String, String> createAccreditation(@PathVariable Long userId, @Valid @RequestBody Map<String, Object> requestBody) {
        String accreditationType = (String) requestBody.get("accreditation_type");
        Map<String, String> documentData = (Map<String, String>) requestBody.get("document");
        Document document = new Document(documentData.get("name"), documentData.get("mime_type"), documentData.get("content"));

        Accreditation accreditation = accreditationService.createAccreditations(userId, accreditationType, document);

        return Map.of("accreditation_id", accreditation.getAccreditationId().toString());
    }

    @PutMapping("/accreditation/{accreditationId}")
    public Map<String, String> finalizedAccreditation(@PathVariable UUID accreditationId, @RequestBody Map<String, String> requestBody) {

        String outcome = requestBody.get("outcome");
        Accreditation accreditation = accreditationService.updateAccreditationStatus(accreditationId, outcome);

        return Map.of("accreditation_id", accreditation.getAccreditationId().toString());
    }

    @GetMapping("/{userId}/accreditation")
    public Map<String, Object> getAccreditation(@PathVariable Long userId) {

        List<Accreditation> accreditations = accreditationService.getAccreditationsByUserId(userId);

        return Map.of(
                "user_id", userId,
                "accreditation_statuses", accreditations.stream().collect(Collectors.toMap(
                        accreditation -> accreditation.getAccreditationId().toString(),
                        accreditation -> Map.of(
                                "accreditation_type", accreditation.getAccreditationType(),
                                "status", accreditation.getStatus()
                        )
                ))
        );
    }
}
