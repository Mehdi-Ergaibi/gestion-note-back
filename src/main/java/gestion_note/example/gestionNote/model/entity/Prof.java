package gestion_note.example.gestionNote.model.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gestion_note.example.gestionNote.model.enumEntities.Semestre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prof")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prof {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection(targetClass = Semestre.class)
    @CollectionTable(name = "professor_semestres", joinColumns = @JoinColumn(name = "professor_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "semestre", nullable = false)
    private List<Semestre> semestres = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "professor_filieres",
        joinColumns = @JoinColumn(name = "professor_id"),
        inverseJoinColumns = @JoinColumn(name = "filiere_id")
    )
    private Set<Filiere> filieres = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "professor_elements",
        joinColumns = @JoinColumn(name = "professor_id"),
        inverseJoinColumns = @JoinColumn(name = "element_id")
    )
    private List<Element> elements = new ArrayList<>();

    private boolean isChef;  // ila kan prof chef de filiere true

    @ManyToMany
    @JoinTable( 
        name = "professor_filieres_coord",
        joinColumns = @JoinColumn(name = "professor_id"),
        inverseJoinColumns = @JoinColumn(name = "filiere_id")
    )
    private List<Filiere> filieresCord = new ArrayList<>(); // les filieres li hoa chef eihom


    public Set<Long> getFiliereIds() {
        Set<Long> filiereIds = new HashSet<>();
        for (Filiere filiere : filieres) {
            filiereIds.add(filiere.getId());
        }
        return filiereIds;
    }
}
