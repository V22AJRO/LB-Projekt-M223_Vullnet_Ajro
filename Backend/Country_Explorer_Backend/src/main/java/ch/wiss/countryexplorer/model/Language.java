package ch.wiss.countryexplorer.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Diese Klasse beschreibt eine Sprache.
 * 
 * Eine Sprache kann mehreren Ländern zugeordnet sein.
 * Beispiel:
 * - "de" → Switzerland, Germany, Austria
 * - "en" → USA, UK, Australia
 * 
 * Der Sprach-Code ist eindeutig und darf nicht doppelt vorkommen.
 */
@Entity
@Table(name = "languages")
public class Language {

    /**
     * Eindeutige ID der Sprache.
     * Wird automatisch von der Datenbank erzeugt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Sprach-Code.
     * Beispiel: "de", "fr", "en"
     * 
     * Pflichtfeld.
     * Muss eindeutig sein.
     */
    @NotBlank(message = "code is required")
    @Size(max = 10, message = "code must be at most 10 characters")
    @Column(nullable = false, unique = true, length = 10)
    private String code;

    /**
     * Voller Name der Sprache.
     * Beispiel: "German", "French"
     * 
     * Pflichtfeld.
     */
    @NotBlank(message = "name is required")
    @Size(max = 255, message = "name must be at most 255 characters")
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Liste der Länder, die diese Sprache verwenden.
     * 
     * Wird mit @JsonIgnore markiert,
     * damit beim Zurückgeben als JSON keine Endlosschleife entsteht.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "languages")
    private Set<Country> countries = new HashSet<>();

    /**
     * Leerer Konstruktor.
     * Wird von JPA benötigt.
     */
    protected Language() {
        // JPA
    }

    /**
     * Konstruktor zum Erstellen einer neuen Sprache.
     * 
     * Erwartet:
     * - code (z.B. "de")
     * - name (z.B. "German")
     */
    public Language(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * Gibt die ID der Sprache zurück.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gibt den Sprach-Code zurück.
     */
    public String getCode() {
        return code;
    }

    /**
     * Setzt oder ändert den Sprach-Code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gibt den Namen der Sprache zurück.
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt oder ändert den Namen der Sprache.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt alle Länder zurück,
     * die diese Sprache verwenden.
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

    /**
     * Zwei Sprachen gelten als gleich,
     * wenn ihr Code gleich ist (Gross-/Kleinschreibung egal).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language other)) return false;
        return code != null && other.code != null && code.equalsIgnoreCase(other.code);
    }

    /**
     * HashCode basiert ebenfalls auf dem Code.
     * Wird intern z.B. in Sets benötigt.
     */
    @Override
    public int hashCode() {
        return Objects.hash(code == null ? null : code.toLowerCase());
    }
}
