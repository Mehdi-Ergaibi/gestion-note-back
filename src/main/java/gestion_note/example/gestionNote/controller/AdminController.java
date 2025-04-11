package gestion_note.example.gestionNote.controller;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gestion_note.example.gestionNote.dto.RegistrationRequest;
import gestion_note.example.gestionNote.dto.UserUpdateRequest;
import gestion_note.example.gestionNote.model.entity.Filiere;
import gestion_note.example.gestionNote.model.entity.Prof;
import gestion_note.example.gestionNote.model.entity.Role;
import gestion_note.example.gestionNote.model.entity.Student;
import gestion_note.example.gestionNote.model.entity.User;
import gestion_note.example.gestionNote.model.enumEntities.ERole;
import gestion_note.example.gestionNote.model.enumEntities.Semestre;
import gestion_note.example.gestionNote.repositories.FiliereRepository;
import gestion_note.example.gestionNote.repositories.ProfRepository;
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

    @Autowired
    ProfRepository profRepository;

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
        if (userRole.getName() == ERole.PROFESSEUR) {
                Prof prof = new Prof();
                prof.setUser(user);
                prof.setChef(request.isChef());

                if(request.isChef()) {
                    Role role = roleRepository.findByName(ERole.COORDONNATEUR).get();
                    prof.getUser().addRole(role);
                }
                
                Set<Filiere> filieres = filiereRepository.findAllById(request.getFiliereIds())
                .stream()
                .collect(Collectors.toSet());
                prof.setFilieres(filieres);
                profRepository.save(prof);
            }
            return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully!"));
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
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest updatedUser) {
        User user = userRepository.findByEmail(updatedUser.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        if (updatedUser.getRoles() != null) {
            Set<Role> updatedRoles = updatedUser.getRoles().stream()
                .map(roleName -> roleRepository.findByName(ERole.valueOf(roleName))
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
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

            if (updatedUser.getStudent().getFiliereId() != null) {
                Filiere filiere = filiereRepository.findById(updatedUser.getStudent().getFiliereId())
                        .orElseThrow(() -> new RuntimeException("Filiere not found"));
                student.setFiliere(filiere);
            }
            

            studentRepository.save(student);
        }

        // ila can prof
        boolean isProf = user.getRoles().stream().anyMatch(role -> role.getName() == ERole.PROFESSEUR);
        if (isProf) {
            User u = userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new RuntimeException("Professor not found"));


            if (updatedUser.getProf() != null) { // chuf baeda yakma null
                u.getProf().setChef(updatedUser.getProf().isChef());

                if (updatedUser.getProf().isChef()) {
                    Role role = roleRepository.findByName(ERole.COORDONNATEUR).get();
                    u.addRole(role); // Add the role to the user
                }

                Set<Filiere> filieres = filiereRepository.findAllById(updatedUser.getProf().getFiliereIds())
                    .stream()
                    .collect(Collectors.toSet());
                    u.getProf().setFilieres(filieres);

            }else {
                throw new RuntimeException("ProfDTO feha chi defaut");
            }


            userRepository.save(u);
        }
        /* System.out.println(updatedUser.getRoles());
        System.out.println(updatedUser.getProf().isChef()); */
        return ResponseEntity.ok(Collections.singletonMap("message", "User updated successfully"));
    }

    @DeleteMapping("/delete-user/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);

        return ResponseEntity.ok(Collections.singletonMap("message", "User deleted successfully"));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/admins")
    public ResponseEntity<List<User>> getAllAdmins() {
        return ResponseEntity.ok(userRepository.findByRole(ERole.ADMIN_APP));
    }

    @GetMapping("/coordinateurs")
    public ResponseEntity<List<User>> getAllCoordinateurs() {
        return ResponseEntity.ok(userRepository.findByRole(ERole.COORDONNATEUR));
    }

    @GetMapping("/profs")
    public ResponseEntity<List<User>> getAllProfs() {
        return ResponseEntity.ok(userRepository.findByRole(ERole.PROFESSEUR));
    }

    @GetMapping("/students")
    public ResponseEntity<List<User>> getAllStudents() {
        return ResponseEntity.ok(userRepository.findByRole(ERole.ETUDIANT));
    }

    @GetMapping("/admins-absence")
    public ResponseEntity<List<User>> getAllAdminsAbsence() {
        return ResponseEntity.ok(userRepository.findByRole(ERole.ADMIN_ABSENCE));
    }

    @GetMapping("/secretaires")
    public ResponseEntity<List<User>> getAllSecretaires() {
        return ResponseEntity.ok(userRepository.findByRole(ERole.SECRETAIRE_GENERAL));
    }

    @GetMapping("/chefs-scolarite")
    public ResponseEntity<List<User>> getAllChefsScolarite() {
        return ResponseEntity.ok(userRepository.findByRole(ERole.CHEF_SCOLARITE));
    }



}
