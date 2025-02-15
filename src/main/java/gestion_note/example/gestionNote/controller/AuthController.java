package gestion_note.example.gestionNote.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gestion_note.example.gestionNote.dto.AuthenticationRequest;
import gestion_note.example.gestionNote.dto.AuthenticationResponse;
import gestion_note.example.gestionNote.model.entity.User;
import gestion_note.example.gestionNote.repositories.UserRepository;
import gestion_note.example.gestionNote.service.JwtService;
import gestion_note.example.gestionNote.service.UserPrincipal;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserRepository userRepository;
        

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        
        User user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("User not found"));
        UserDetails userDetails = new UserPrincipal(user);
        String token = jwtService.generateToken(userDetails);
        
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    /* @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already registered");
        }
        
        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // role par defaut etito etudiant tla man baed w nchuf kifach momken itbadal
        Optional<Role> userRoleOpt = roleRepository.findByName(ERole.ETUDIANT);
        if (!userRoleOpt.isPresent()) {
            return ResponseEntity.badRequest().body("User role not configured");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(userRoleOpt.get());
        newUser.setRoles(roles);
        
        userRepository.save(newUser);
        
        return ResponseEntity.ok("User registered successfully");
    } */

}
