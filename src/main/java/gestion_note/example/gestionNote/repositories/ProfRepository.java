package gestion_note.example.gestionNote.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gestion_note.example.gestionNote.model.entity.Prof;
import gestion_note.example.gestionNote.model.entity.User;


@Repository
public interface ProfRepository extends JpaRepository<Prof, Long> {
    Optional<Prof> findByUser(User user);

}
