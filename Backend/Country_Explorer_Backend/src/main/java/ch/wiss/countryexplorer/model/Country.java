package ch.wiss.countryexplorer.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Diese Klasse beschreibt ein Land.
 * 
 * Ein Country wird in der Datenbank gespeichert
 * und gehört immer genau zu einer Region.
 * 
 * Zusätzlich kann ein Land mehrere Sprachen haben.
 * Eine Sprache kann aber auch in mehreren Ländern vorkommen.
 * 
 * Beispiel:
 * Code: "CHE"
 * Name: "Switzerland"
 * Region: Europe
 */
@Entity
@Table(name = "countries")
public class Country {

    /**
     * Eindeutige ID des Landes.
     * Wird automatisch von der Datenbank erzeugt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ländercode mit genau 3 Zeichen.
     * Beispiel: CHE, DEU, FRA
     * 
     * Darf nicht leer sein.
     * Muss eindeutig sein.
     */
    @NotBlank(message = "code is required")
    @Size(min = 3, max = 3, message = "code must be exactly 3 characters")
    @Column(nullable = false, unique = true, length = 3)
    private String code;

    /**
     * Vollständiger Name des Landes.
     * Beispiel: Switzerland, Germany
     * 
     * Darf nicht leer sein.
     */
    @NotBlank(message = "name is required")
    @Size(max = 255, message = "name must be at most 255 characters")
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Region, zu der dieses Land gehört.
     * 
     * Jedes Land muss genau eine Region haben.
     * Beispiel: Europe, Asia, Americas
     */
    @NotNull(message = "region is required")
    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    /**
     * Liste der Sprachen, die in diesem Land gesprochen werden.
     * 
     * Beispiel:
     * Schweiz → Deutsch, Französisch, Italienisch
     * 
     * Many-to-Many Beziehung:
     * Ein Land kann mehrere Sprachen haben,
     * eine Sprache kann in mehreren Ländern vorkommen.
     */
    @ManyToMany
    @JoinTable(
        name = "country_languages",
        joinColumns = @JoinColumn(name = "country_id"),
        inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Set<Language> languages = new HashSet<>();

    /**
     * Leerer Konstruktor.
     * Wird von JPA benötigt.
     */
    protected Country() {
        // JPA
    }

    /**
     * Konstruktor zum Erstellen eines neuen Landes.
     * 
     * Erwartet:
     * - code (z.B. CHE)
     * - name (z.B. Switzerland)
     * - region (z.B. Europe)
     */
    public Country(String code, String name, Region region) {
        this.code = code;
        this.name = name;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    /**
     * Gibt den 3-stelligen Ländercode zurück.
     */
    public String getCode() {
        return code;
    }

    /**
     * Setzt den Ländercode.
     * Muss genau 3 Zeichen haben.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gibt den Namen des Landes zurück.
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Landes.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die zugehörige Region zurück.
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Setzt die Region des Landes.
     */
    public void setRegion(Region region) {
        this.region = region;
    }

    /**
     * Gibt alle Sprachen des Landes zurück.
     */
    public Set<Language> getLanguages() {
        return languages;
    }

    /**
     * Setzt die Sprachen neu.
     * Falls null übergeben wird,
     * wird eine leere Liste gesetzt.
     */
    public void setLanguages(Set<Language> languages) {
        this.languages = (languages == null) ? new HashSet<>() : languages;
    }
}
