package gestion_note.example.gestionNote.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gestion_note.example.gestionNote.dto.FiliereDTO;
import gestion_note.example.gestionNote.dto.ModuleDTO;
import gestion_note.example.gestionNote.model.entity.Element;
import gestion_note.example.gestionNote.model.entity.Filiere;
import gestion_note.example.gestionNote.service.FiliereService;

@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasRole('ADMIN_APP')")

public class FiliereController {

    @Autowired
    FiliereService filiereService;

    @PostMapping
    public ResponseEntity<Filiere> createFiliere(@RequestBody Filiere filiere) {
        return ResponseEntity.ok(filiereService.createFiliere(filiere));
    }

    @PutMapping("/filiere/{id}")
    public ResponseEntity<Filiere> updateFiliere(@PathVariable Long id, @RequestBody Filiere filiereDetails) {
        return ResponseEntity.ok(filiereService.updateFiliere(id, filiereDetails));
    }

    @DeleteMapping("/filiere/{id}")
public ResponseEntity<Map<String, String>> deleteFiliere(@PathVariable Long id) {
    filiereService.deleteFiliere(id);
    return ResponseEntity.ok()
        .body(Collections.singletonMap("message", "Filière supprimée avec succès"));
}

    @GetMapping("/filiere")
    public ResponseEntity<List<FiliereDTO>> getAllFilieres() {
        List<Filiere> filieres = filiereService.getAllFilieres();

        List<FiliereDTO> filiereDTOs = filieres.stream().map(f -> {
            FiliereDTO dto = new FiliereDTO();
            dto.setId(f.getId());
            dto.setName(f.getName());
            dto.setSemestre(f.getSemestre().toString());

            // Map modules and their elements
            List<ModuleDTO> moduleDTOs = f.getModules().stream().map(m -> {
                ModuleDTO moduleDTO = new ModuleDTO();
                moduleDTO.setName(m.getName());
                moduleDTO.setElements(m.getElements().stream().map(Element::getName).toList());
                return moduleDTO;
            }).toList();
            dto.setModules(moduleDTOs);

            // Map professor names
            List<String> profNames = f.getProfessors().stream()
                    .map(p -> p.getUser().getFirstName() + " " + p.getUser().getLastName())
                    .toList();
            dto.setProfessorNames(profNames);

            return dto;
        }).toList();

        return ResponseEntity.ok(filiereDTOs);
    }

}
