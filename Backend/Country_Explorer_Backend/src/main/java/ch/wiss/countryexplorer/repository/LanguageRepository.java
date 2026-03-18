package ch.wiss.countryexplorer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.wiss.countryexplorer.model.Language;

/**
 * Dieses Repository kümmert sich um den Zugriff auf die Tabelle "languages".
 * 
 * Hier werden Sprachen gespeichert und abgefragt.
 * 
 * Durch JpaRepository stehen automatisch Standard-Methoden zur Verfügung:
 * - findAll() → alle Sprachen laden
 * - findById() → Sprache per ID suchen
 * - save() → neue Sprache speichern oder bestehende ändern
 */
public interface LanguageRepository extends JpaRepository<Language, Long> {

    /**
     * Sucht eine Sprache anhand ihres Codes.
     * 
     * Beispiel:
     * findByCodeIgnoreCase("de")
     * → findet die Sprache "German"
     * 
     * IgnoreCase bedeutet:
     * Gross- und Kleinschreibung wird nicht berücksichtigt.
     * 
     * Optional wird verwendet, weil:
     * - Die Sprache existieren kann
     * - Oder eben nicht
     */
    Optional<Language> findByCodeIgnoreCase(String code);

    /**
     * Prüft, ob eine Sprache mit diesem Code bereits existiert.
     * 
     * Beispiel:
     * existsByCodeIgnoreCase("fr")
     * → true, wenn Französisch bereits gespeichert ist.
     * 
     * Diese Methode ist wichtig,
     * damit kein doppelter Sprachcode gespeichert wird.
     */
    boolean existsByCodeIgnoreCase(String code);

}
