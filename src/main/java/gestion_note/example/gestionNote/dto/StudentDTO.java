package gestion_note.example.gestionNote.dto;

import gestion_note.example.gestionNote.model.enumEntities.Semestre;
import lombok.Data;

@Data
public class StudentDTO {
    private String cne;
    private Semestre semestre;
    private Long filiereId;
}
