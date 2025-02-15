package gestion_note.example.gestionNote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gestion_note.example.gestionNote.model.entity.Filiere;
import gestion_note.example.gestionNote.repositories.FiliereRepository;

@Service
public class FiliereService {

    @Autowired
    private FiliereRepository filiereRepository;


    public List<Filiere> getAllFilieres() {
        return filiereRepository.findAll();
    }

    public Filiere createFiliere(Filiere filiere) {
        return filiereRepository.save(filiere);
    }

    public Filiere updateFiliere(Long id, Filiere filiereDetails) {
        Filiere filiere = filiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filiere not found"));
        filiere.setName(filiereDetails.getName());
        return filiereRepository.save(filiere);
    }

    public void deleteFiliere(Long id) {
        filiereRepository.deleteById(id);
    }
}