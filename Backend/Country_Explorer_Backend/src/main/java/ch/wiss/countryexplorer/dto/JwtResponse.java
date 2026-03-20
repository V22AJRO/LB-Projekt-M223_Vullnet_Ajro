package ch.wiss.countryexplorer.dto;

import java.util.List;

/**
 * Diese Klasse beschreibt die Login-Antwort,
 * die das Backend nach erfolgreicher Anmeldung an das Frontend zurückgibt.
 *
 * Enthalten sind:
 * - das JWT Token
 * - der Tokentyp
 * - die Benutzer-ID
 * - der Username
 * - die Rollen des Benutzers
 */

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String userName, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = userName;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gibt den Typ des Tokens zurück.
     *
     * Hier ist das immer "Bearer".
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gibt die ID des angemeldeten Benutzers zurück.
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gibt den Username des angemeldeten Benutzers zurück.
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gibt die Rollen des Benutzers zurück.
     */
    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}