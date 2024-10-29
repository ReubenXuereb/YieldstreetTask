package com.example.Yieldstreeet.YieldstreetTask.controller;

import com.example.Yieldstreeet.YieldstreetTask.service.AccreditationScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
public class AccreditationSchedulerController {

    private final AccreditationScheduler accreditationScheduler;

    public AccreditationSchedulerController(AccreditationScheduler accreditationScheduler) {
        this.accreditationScheduler = accreditationScheduler;
    }

    @PostMapping("/run")
    public ResponseEntity<String> runManualCronJob() {
        accreditationScheduler.expiredAccreditations();
        return ResponseEntity.ok("Cronjob executed manually.");
    }
}
