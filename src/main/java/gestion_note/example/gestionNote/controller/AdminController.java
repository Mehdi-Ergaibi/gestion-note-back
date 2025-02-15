package gestion_note.example.gestionNote.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gestion_note.example.gestionNote.dto.RegistrationRequest;
import gestion_note.example.gestionNote.model.entity.Filiere;
import gestion_note.example.gestionNote.model.entity.Role;
import gestion_note.example.gestionNote.model.entity.Student;
import gestion_note.example.gestionNote.model.entity.User;
import gestion_note.example.gestionNote.model.enumEntities.ERole;
import gestion_note.example.gestionNote.model.enumEntities.Semestre;
import gestion_note.example.gestionNote.repositories.FiliereRepository;
import gestion_note.example.gestionNote.repositories.RoleRepository;
import gestion_note.example.gestionNote.repositories.StudentRepository;
import gestion_note.example.gestionNote.repositories.UserRepository;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN_APP')")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    FiliereRepository filiereRepository;

    @PostMapping("/add-user")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(userRole);
        userRepository.save(user);

        if (userRole.getName() == ERole.ETUDIANT) {
            Student student = new Student();
            student.setUser(user);
            student.setCne(request.getCne());
            student.setFiliere(filiereRepository.findById(request.getFiliereId()).orElse(null));
            student.setSemestre(Semestre.valueOf(request.getSemestre())); 
            studentRepository.save(student);
        }

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/assign-role")
    public ResponseEntity<?> assignRole(@RequestParam String email, @RequestParam ERole roleName) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.addRole(role);
        userRepository.save(user);

        return ResponseEntity.ok("Role assigned successfully");
    }

    @PostMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser) {
        User user = userRepository.findByEmail(updatedUser.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        if (updatedUser.getRoles() != null && !updatedUser.getRoles().isEmpty()) {
            Set<Role> updatedRoles = updatedUser.getRoles().stream()
                    .map(role -> roleRepository.findByName(role.getName())
                            .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName())))
                    .collect(Collectors.toSet());

            user.setRoles(updatedRoles);
        }

        userRepository.save(user);

        // ila kan user rah student updati dakchi li zayen end student
        boolean isStudent = user.getRoles().stream().anyMatch(role -> role.getName() == ERole.ETUDIANT);
        if (isStudent) {
            Student student = studentRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            student.setCne(updatedUser.getStudent().getCne());
            student.setSemestre(updatedUser.getStudent().getSemestre());

            if (updatedUser.getStudent().getFiliere() != null) {
                Filiere filiere = filiereRepository.findById(updatedUser.getStudent().getFiliere().getId())
                        .orElseThrow(() -> new RuntimeException("Filiere not found"));
                student.setFiliere(filiere);
            }

            studentRepository.save(student);
        }

        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);

        return ResponseEntity.ok("User deleted successfully");
    }

}
