package gestion_note.example.gestionNote.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gestion_note.example.gestionNote.model.entity.Element;
import gestion_note.example.gestionNote.repositories.ElementRepository;
import gestion_note.example.gestionNote.repositories.ModuleRepository;
import gestion_note.example.gestionNote.model.entity.Module;

@Service
public class ElementService {

    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    public Element createElement(Long moduleId, Element element) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));
        element.setModule(module);
        return elementRepository.save(element);
    }

    public Element updateElement(Long id, Element elementDetails) {
        Element element = elementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Element not found"));
        element.setName(elementDetails.getName());
        return elementRepository.save(element);
    }

    public void deleteElement(Long id) {
        elementRepository.deleteById(id);
    }

    public List<Element> getAllElements() {
        return elementRepository.findAll();
    }

    public Optional<Element> getElementById(Long id) {
        return elementRepository.findById(id);
    }

}
