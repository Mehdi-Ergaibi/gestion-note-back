package gestion_note.example.gestionNote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gestion_note.example.gestionNote.model.entity.ConfiguationEnabling;
import gestion_note.example.gestionNote.service.ConfigurationService;

@RestController
@RequestMapping("api/professeur")
@PreAuthorize("hasRole('PROFESSEUR')")
public class profController {

    @Autowired
    ConfigurationService configurationService;

    @PostMapping("/grades")
    public ResponseEntity<?> enterGrade(@RequestParam String type) {
        ConfiguationEnabling config = configurationService.getConfiguration();
        
        // controle continu
        if ("CONTROLE_CONTINU".equalsIgnoreCase(type)) {
            // Logic to save the grade
            System.out.println("adding cc");
            return ResponseEntity.ok("Contrôle continu grade added successfully");
        }

        // normal
        if ("EXAMEN_NORMAL".equalsIgnoreCase(type) && !config.isNormalExamGradeEntryEnabled()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Normal exam grade entry is disabled.");
        }

        // ratt
        if ("EXAMEN_RATTRAPAGE".equalsIgnoreCase(type) && !config.isRattExamGradeEntryEnabled()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Retake exam grade entry is disabled.");
        }

        return ResponseEntity.ok("Grade added successfully");
    }

}
