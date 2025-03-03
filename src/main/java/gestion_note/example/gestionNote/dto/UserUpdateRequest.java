package gestion_note.example.gestionNote.dto;

import java.util.Set;

import lombok.Data;

@Data

public class UserUpdateRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> roles; // Use role names instead of Role objects
    private StudentDTO student;
    private ProfDTO prof;
}
