package gestion_note.example.gestionNote.repositories;

import gestion_note.example.gestionNote.model.entity.Filiere;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    
}