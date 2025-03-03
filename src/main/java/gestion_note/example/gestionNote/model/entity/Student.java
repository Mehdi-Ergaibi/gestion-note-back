package gestion_note.example.gestionNote.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import gestion_note.example.gestionNote.model.enumEntities.Semestre;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonBackReference("studentReference")
    private User user;

    @Column(nullable = false, unique = true)
    private String cne;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    @JsonBackReference
    private Filiere filiere;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Semestre semestre;

    @ManyToMany
    @JoinTable(
        name = "student_modules",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    @JsonBackReference
    private List<Module> modules = new ArrayList<>();
}