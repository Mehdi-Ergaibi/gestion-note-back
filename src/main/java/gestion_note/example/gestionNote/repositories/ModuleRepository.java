package gestion_note.example.gestionNote.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gestion_note.example.gestionNote.model.entity.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
}