package gestion_note.example.gestionNote.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import gestion_note.example.gestionNote.model.enumEntities.Semestre;

@Entity
@Table(name = "modules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "filiere_id", nullable = false)
    @JsonBackReference
    private Filiere filiere;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Semestre semestre;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<Element> elements = new HashSet<>();
}
