package gestion_note.example.gestionNote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gestion_note.example.gestionNote.model.entity.Filiere;
import gestion_note.example.gestionNote.repositories.FiliereRepository;
import jakarta.persistence.EntityManager;

@Service
@Transactional
public class FiliereService {

    @Autowired
    private FiliereRepository filiereRepository;


    @Autowired
    private EntityManager entityManager;



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

    public void deleteFiliere(Long filiereId) {
        Filiere filiere = filiereRepository.findById(filiereId)
                .orElseThrow(() -> new RuntimeException("Filière not found"));

        // 1. Clear all relationships using verified table/column names
        clearJoinTables(filiereId);

        // 2. Delete the filiere
        filiereRepository.delete(filiere);
    }

    private void clearJoinTables(Long filiereId) {
        // Clear professor associations
        entityManager.createNativeQuery("DELETE FROM professor_filieres WHERE filiere_id = :filiereId")
                    .setParameter("filiereId", filiereId)
                    .executeUpdate();

        // Clear coordinator relationships
        entityManager.createNativeQuery("DELETE FROM professor_filieres_coord WHERE filiere_id = :filiereId")
                    .setParameter("filiereId", filiereId)
                    .executeUpdate();

        // Clear student-module relationships using correct student ID column name
        entityManager.createNativeQuery(
            "DELETE FROM student_modules WHERE student_id IN " +
            "(SELECT user_id FROM students WHERE filiere_id = :filiereId)")  // Changed to user_id
            .setParameter("filiereId", filiereId)
            .executeUpdate();

        // Update students' filiere reference
        entityManager.createNativeQuery("UPDATE students SET filiere_id = NULL WHERE filiere_id = :filiereId")
                    .setParameter("filiereId", filiereId)
                    .executeUpdate();
    }

}