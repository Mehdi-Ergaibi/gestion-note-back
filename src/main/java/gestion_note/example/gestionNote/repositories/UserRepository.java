package gestion_note.example.gestionNote.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gestion_note.example.gestionNote.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
    Boolean existsByEmail(String email);
}
