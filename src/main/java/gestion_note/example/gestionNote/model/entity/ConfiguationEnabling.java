package gestion_note.example.gestionNote.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfiguationEnabling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean normalExamGradeEntryEnabled;   // admin yaeti true wla false b nssba l normal
    private boolean rattExamGradeEntryEnabled; // bnssba l ratt
    private boolean studentGradeDisplayEnabled; // talaba ichofo noqat
}
