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

import gestion_note.example.gestionNote.model.entity.Element;
import gestion_note.example.gestionNote.service.ElementService;

@RestController
@RequestMapping("api/admin/elements")
public class ElementController {

    @Autowired
    ElementService elementService;

    @PostMapping("/{moduleId}/elements")
    public ResponseEntity<Element> createElement(@PathVariable Long moduleId, @RequestBody Element element) {
        Element createdElement = elementService.createElement(moduleId, element);
        return ResponseEntity.ok(createdElement);
    }

    @PutMapping("/elements/{id}")
    public ResponseEntity<Element> updateElement(@PathVariable Long id, @RequestBody Element elementDetails) {
        Element updatedElement = elementService.updateElement(id, elementDetails);
        return ResponseEntity.ok(updatedElement);
    }

    @DeleteMapping("/elements/{id}")
    public ResponseEntity<?> deleteElement(@PathVariable Long id) {
        elementService.deleteElement(id);
        return ResponseEntity.ok("Element deleted successfully");
    }

    @GetMapping
    public List<Element> getAllElements() {
        return elementService.getAllElements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Element> getElementById(@PathVariable Long id) {
        return ResponseEntity.of(elementService.getElementById(id));
    }

}
