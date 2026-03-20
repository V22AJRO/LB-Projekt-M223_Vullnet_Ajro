package ch.wiss.countryexplorer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.wiss.countryexplorer.model.User;

/**
 * Dieses Repository kümmert sich um den Zugriff auf Benutzer.
 *
 * JpaRepository bringt bereits viele Standardmethoden mit:
 * - findAll()
 * - findById()
 * - save()
 * - delete()
 *
 * Zusätzlich werden hier projektspezifische Suchmethoden definiert.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Sucht einen Benutzer anhand des Usernamens.
     */
    Optional<User> findByUsername(String username);

    /**
     * Prüft, ob ein Benutzername bereits existiert.
     */
    Boolean existsByUsername(String username);
}