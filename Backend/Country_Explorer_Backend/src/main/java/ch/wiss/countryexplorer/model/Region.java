package ch.wiss.countryexplorer.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Diese Klasse beschreibt eine Region.
 * 
 * Eine Region ist zum Beispiel:
 * - Europe
 * - Asia
 * - Americas
 * 
 * Eine Region kann mehrere Länder enthalten.
 * Ein Land gehört aber immer genau zu einer Region.
 */
@Entity
@Table(name = "regions")
public class Region {

    /**
     * Eindeutige ID der Region.
     * Wird automatisch von der Datenbank erzeugt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name der Region.
     * Beispiel: "Europe"
     * 
     * Darf nicht leer sein.
     * Muss eindeutig sein (keine Region doppelt).
     */
    @NotBlank(message = "name is required")
    @Size(max = 255, message = "name must be at most 255 characters")
    @Column(nullable = false, unique = true, length = 255)
    private String name;

    /**
     * Liste aller Länder, die zu dieser Region gehören.
     * 
     * Beispiel:
     * Europe → Switzerland, Germany, France
     * 
     * @JsonIgnore verhindert,
     * dass beim Zurückgeben als JSON eine Endlosschleife entsteht
     * (Region → Country → Region → Country ...).
     */
    @JsonIgnore
    @OneToMany(mappedBy = "region")
    private Set<Country> countries = new HashSet<>();

    /**
     * Leerer Konstruktor.
     * Wird von JPA benötigt.
     */
    protected Region() {
        // JPA
    }

    /**
     * Konstruktor zum Erstellen einer neuen Region.
     * 
     * Erwartet:
     * - name (z.B. "Europe")
     */
    public Region(String name) {
        this.name = name;
    }

    /**
     * Gibt die ID der Region zurück.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gibt den Namen der Region zurück.
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt oder ändert den Namen der Region.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt alle Länder zurück,
     * die zu dieser Region gehören.
     */
    public Set<Country> getCountries() {
        return countries;
    }

    /**
     * Setzt die Länder neu.
     * Falls null übergeben wird,
     * wird eine leere Liste gesetzt.
     */
    public void setCountries(Set<Country> countries) {
        this.countries = (countries == null) ? new HashSet<>() : countries;
    }
}
