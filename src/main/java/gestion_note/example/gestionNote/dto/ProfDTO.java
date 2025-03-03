package gestion_note.example.gestionNote.dto;

import java.util.Set;
import lombok.Data;

@Data
public class ProfDTO {
    private boolean isChef;
    private Set<Long> filiereIds;
}

