package ch.wiss.countryexplorer.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.countryexplorer.dto.JwtResponse;
import ch.wiss.countryexplorer.dto.LoginRequest;
import ch.wiss.countryexplorer.dto.SignupRequest;
import ch.wiss.countryexplorer.model.ERole;
import ch.wiss.countryexplorer.model.Role;
import ch.wiss.countryexplorer.model.User;
import ch.wiss.countryexplorer.repository.RoleRepository;
import ch.wiss.countryexplorer.repository.UserRepository;
import ch.wiss.countryexplorer.security.JwtUtils;
import ch.wiss.countryexplorer.security.UserDetailsImpl;
import jakarta.validation.Valid;

/**
 * Dieser Controller ist für die Authentifizierung zuständig.
 *
 * Hier werden insbesondere zwei Dinge verarbeitet:
 * - Login eines bestehenden Benutzers
 * - Registrierung eines neuen Benutzers
 *
 * Die Klasse ist mit @RestController annotiert.
 * Das bedeutet:
 * Spring behandelt diese Klasse als REST-Endpunkt
 * und gibt Rückgabewerte direkt als JSON zurück.
 *
 * @RequestMapping("/api/auth") legt den gemeinsamen Basis-Pfad fest.
 * Alle Methoden in dieser Klasse hängen an diesem Pfad.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Der AuthenticationManager prüft,
     * ob Benutzername und Passwort korrekt sind.
     */
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * Repository für Benutzerzugriffe.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Repository für Rollen.
     */
    @Autowired
    RoleRepository roleRepository;

    /**
     * Encoder zum sicheren Hashen von Passwörtern.
     */
    @Autowired
    PasswordEncoder encoder;

    /**
     * Hilfsklasse zum Erstellen und Prüfen von JWT Tokens.
     */
    @Autowired
    JwtUtils jwtUtils;

    /**
     * Führt den Login eines Benutzers durch.
     *
     * @PostMapping("/login") bedeutet:
     * Diese Methode reagiert auf HTTP POST Requests
     * an /api/auth/login.
     *
     * @RequestBody liest den JSON-Body der Anfrage ein
     * und wandelt ihn in ein LoginRequest-Objekt um.
     *
     * @Valid aktiviert Validierungsregeln,
     * falls solche im DTO definiert sind.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),
                        request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateToken(request.getUsername());

        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = user.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse(token, user.getId(), user.getUsername(), roles);

        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Registriert einen neuen Benutzer.
     *
     * In diesem Projekt werden nur noch folgende Felder verwendet:
     * - username
     * - password
     * - roles
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        User user = new User(request.getUsername(),
                encoder.encode(request.getPassword()));

        Set<String> strRoles = request.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}