package ch.wiss.countryexplorer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ch.wiss.countryexplorer.model.Country;

/**
 * Dieses Repository kümmert sich um den Zugriff auf die Tabelle "countries".
 * 
 * Hier werden Länder aus der Datenbank gelesen, gespeichert
 * oder geprüft, ob etwas bereits existiert.
 * 
 * JpaRepository bringt automatisch viele Standard-Methoden mit:
 * - findAll()
 * - findById()
 * - save()
 * - delete()
 * usw.
 * 
 * Zusätzlich sind hier eigene Suchmethoden definiert.
 */
public interface CountryRepository extends JpaRepository<Country, Long> {

    /**
     * Prüft, ob es bereits ein Land mit diesem Code gibt.
     * 
     * Beispiel:
     * existsByCodeIgnoreCase("CHE")
     * → true, wenn der Code schon existiert.
     */
    boolean existsByCodeIgnoreCase(String code);

    /**
     * Gibt alle Länder zurück.
     * 
     * Wichtig:
     * region wird direkt mitgeladen.
     * Dadurch entsteht später kein LazyLoading-Fehler.
     */
    @Override
    @EntityGraph(attributePaths = {"region"})
    List<Country> findAll();

    /**
     * Gibt genau ein Land anhand der ID zurück.
     * 
     * Auch hier wird region direkt mitgeladen.
     */
    @Override
    @EntityGraph(attributePaths = {"region"})
    Optional<Country> findById(Long id);

    /**
     * Sucht Länder nach Region.
     * 
     * Beispiel:
     * findByRegion_NameIgnoreCase("Europe")
     * → liefert alle Länder in Europa.
     */
    @EntityGraph(attributePaths = {"region"})
    List<Country> findByRegion_NameIgnoreCase(String regionName);

    /**
     * Sucht Länder nach einem Teil des Namens.
     * 
     * Beispiel:
     * findByNameContainingIgnoreCase("land")
     * → findet Switzerland, Finland, Iceland usw.
     */
    @EntityGraph(attributePaths = {"region"})
    List<Country> findByNameContainingIgnoreCase(String name);

    /**
     * Kombinierte Suche:
     * Region + Teil vom Namen.
     * 
     * Beispiel:
     * Region = Europe
     * Name = land
     */
    @EntityGraph(attributePaths = {"region"})
    List<Country> findByRegion_NameIgnoreCaseAndNameContainingIgnoreCase(String regionName, String name);
}