package ch.wiss.countryexplorer.dto;

import java.util.Set;

/**
 * Diese Klasse beschreibt die Daten,
 * die beim Registrieren eines neuen Benutzers
 * an das Backend gesendet werden.
 *
 * In diesem Projekt werden dafür nur verwendet:
 * - username
 * - password
 * - roles
 */
public class SignupRequest {

    private String username;
    private String password;
    private Set<String> roles;

    public SignupRequest() {
    }

    /**
     * Gibt den gewünschten Benutzernamen zurück.
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gibt das Passwort zurück.
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gibt die gewünschten Rollen zurück.
     */
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}