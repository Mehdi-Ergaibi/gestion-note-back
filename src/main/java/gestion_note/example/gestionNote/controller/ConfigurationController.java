package gestion_note.example.gestionNote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gestion_note.example.gestionNote.service.ConfigurationService;

@RestController
@RequestMapping("api/admin/configuration")
public class ConfigurationController {

    @Autowired
    ConfigurationService configurationService;


    @GetMapping
    public ResponseEntity<?> getConfiguration() {
        return ResponseEntity.ok(configurationService.getConfiguration());
    }

    @PutMapping("/normal-exam")
    public ResponseEntity<?> updateNormalExamGradeEntry(@RequestParam boolean enabled) {
        configurationService.updateNormalExamGradeEntry(enabled);
        return ResponseEntity.ok("Normal exam grade entry status updated");
    }

    @PutMapping("/ratt-exam")
    public ResponseEntity<?> updateRetakeExamGradeEntry(@RequestParam boolean enabled) {
        configurationService.updateRattExamGradeEntry(enabled);
        return ResponseEntity.ok("ratt exam grade entry status updated");
    }

    @PutMapping("/student-notes")
    public ResponseEntity<?> updateStudentEnabling(@RequestParam boolean enabled) {
        configurationService.updateStudentGradeDisplayed(enabled);
        return ResponseEntity.ok("ratt exam grade entry status updated");
    }
}
