package gestion_note.example.gestionNote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gestion_note.example.gestionNote.model.entity.Filiere;
import gestion_note.example.gestionNote.service.FiliereService;

@RestController
@RequestMapping("api/admin/filiere")
public class FiliereController {

    @Autowired
    FiliereService filiereService;

    @PostMapping
    public ResponseEntity<Filiere> createFiliere(@RequestBody Filiere filiere) {
        return ResponseEntity.ok(filiereService.createFiliere(filiere));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filiere> updateFiliere(@PathVariable Long id, @RequestBody Filiere filiereDetails) {
        return ResponseEntity.ok(filiereService.updateFiliere(id, filiereDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFiliere(@PathVariable Long id) {
        filiereService.deleteFiliere(id);
        return ResponseEntity.ok("Filiere deleted successfully");
    }

    @GetMapping
    public List<Filiere> getAllFilieres(){
        return filiereService.getAllFilieres();
    }

}
