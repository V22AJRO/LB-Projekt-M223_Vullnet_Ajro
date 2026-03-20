package ch.wiss.countryexplorer.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ch.wiss.countryexplorer.model.ERole;
import ch.wiss.countryexplorer.model.Role;
import ch.wiss.countryexplorer.model.User;
import ch.wiss.countryexplorer.repository.RoleRepository;
import ch.wiss.countryexplorer.repository.UserRepository;

/**
 * Diese Klasse wird beim Start der Anwendung automatisch ausgeführt.
 *
 * Ziel:
 * - fehlende Rollen automatisch anlegen
 * - Testbenutzer user1 automatisch anlegen
 * - Admin-Benutzer admin automatisch anlegen
 *
 * Dadurch ist das Projekt nach dem Start direkt testbar,
 * ohne dass Benutzer zuerst manuell erstellt werden müssen.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Diese Methode wird automatisch einmal beim Start der Anwendung ausgeführt.
     */
    @Override
    public void run(String... args) throws Exception {
        createRoleIfMissing(ERole.ROLE_USER);
        createRoleIfMissing(ERole.ROLE_ADMIN);

        createUserIfMissing("user1", "123456", ERole.ROLE_USER);
        createUserIfMissing("admin", "123456", ERole.ROLE_ADMIN);
    }

    /**
     * Erstellt eine Rolle nur dann,
     * wenn sie noch nicht in der Datenbank vorhanden ist.
     */
    private void createRoleIfMissing(ERole roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role(roleName);
            roleRepository.save(role);
        }
    }

    /**
     * Erstellt einen Benutzer nur dann,
     * wenn der Benutzername noch nicht existiert.
     *
     * Das Passwort wird mit BCrypt gehasht gespeichert.
     */
    private void createUserIfMissing(String username, String rawPassword, ERole roleName) {
        if (userRepository.existsByUsername(username)) {
            return;
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Rolle wurde nicht gefunden: " + roleName));

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
    }
}