package gestion_note.example.gestionNote.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gestion_note.example.gestionNote.model.entity.Filiere;
import gestion_note.example.gestionNote.model.entity.Student;
import gestion_note.example.gestionNote.model.entity.User;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(User user);
    List<Student> findByFiliere(Filiere filiere);
}
