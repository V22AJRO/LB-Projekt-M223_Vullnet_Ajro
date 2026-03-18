package ch.wiss.countryexplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.wiss.countryexplorer.model.Region;

/**
 * Dieses Repository kümmert sich um den Zugriff auf die Tabelle "regions".
 * 
 * Hier können Regionen gespeichert, gelesen oder geprüft werden.
 * 
 * JpaRepository bringt automatisch viele Standard-Methoden mit:
 * - findAll()
 * - findById()
 * - save()
 * - delete()
 * usw.
 */
public interface RegionRepository extends JpaRepository<Region, Long> {

    /**
     * Prüft, ob es bereits eine Region mit diesem Namen gibt.
     * 
     * Beispiel:
     * existsByNameIgnoreCase("Europe")
     * → true, wenn eine Region mit diesem Namen existiert.
     * 
     * IgnoreCase bedeutet:
     * Gross- und Kleinschreibung wird nicht unterschieden.
     */
    boolean existsByNameIgnoreCase(String name);

}
