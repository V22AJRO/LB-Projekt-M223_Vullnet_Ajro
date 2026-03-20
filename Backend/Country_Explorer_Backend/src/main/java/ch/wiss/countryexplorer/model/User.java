package ch.wiss.countryexplorer.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Diese Klasse beschreibt einen Benutzer der Anwendung.
 *
 * Ein Benutzer kann sich anmelden und besitzt Rollen.
 * Über diese Rollen wird später entschieden,
 * welche Funktionen aufgerufen werden dürfen.
 *
 * In diesem Projekt werden nur noch folgende Daten verwendet:
 * - username
 * - password
 * - roles
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Eindeutige ID des Benutzers.
     * Wird automatisch von der Datenbank erzeugt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Benutzername für Login und Anzeige.
     */
    @NotBlank
    private String username;

    /**
     * Passwort des Benutzers.
     *
     * Im Code wird das Passwort gehasht gespeichert
     * und nicht im Klartext.
     */
    @NotBlank
    private String password;

    /**
     * Rollen des Benutzers.
     *
     * Ein Benutzer kann mehrere Rollen besitzen,
     * auch wenn im aktuellen Projekt hauptsächlich
     * nur ROLE_USER oder ROLE_ADMIN verwendet wird.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    /**
     * Konstruktor zum Erstellen eines neuen Benutzers.
     */
    public User(String name, String password) {
        this.username = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    /**
     * Setzt die ID.
     * Normalerweise macht dies automatisch die Datenbank.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gibt den Benutzernamen zurück.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setzt den Benutzernamen.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gibt das Passwort zurück.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setzt das Passwort.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gibt die Rollen des Benutzers zurück.
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Setzt die Rollen des Benutzers.
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}