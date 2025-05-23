package gestion_note.example.gestionNote.dto;

import java.util.List;

import gestion_note.example.gestionNote.model.enumEntities.ERole;
import lombok.Data;

@Data
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ERole role;
    // ila kan user etudiant
    private String cne;  
    private Long filiereId;
    private String semestre;
    // ila kan user Prof
    private boolean isChef;
    private List<Long> filiereIds;
}
