package gestion_note.example.gestionNote.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiliereDTO {
    private Long id;
    private String name;
    private String semestre;
    private List<ModuleDTO> modules;
    private List<String> professorNames;
}
