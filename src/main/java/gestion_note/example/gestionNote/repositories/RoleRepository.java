package gestion_note.example.gestionNote.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gestion_note.example.gestionNote.model.entity.Role;
import gestion_note.example.gestionNote.model.enumEntities.ERole;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}