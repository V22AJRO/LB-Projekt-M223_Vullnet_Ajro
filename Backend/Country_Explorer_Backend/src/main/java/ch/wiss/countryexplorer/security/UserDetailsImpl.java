package ch.wiss.countryexplorer.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.wiss.countryexplorer.model.User;

/**
 * Diese Klasse übersetzt das eigene User-Objekt
 * in ein UserDetails-Objekt von Spring Security.
 *
 * Spring Security benötigt genau diese Form,
 * um einen Benutzer intern für Login und Rollenprüfung zu verwenden.
 */
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;

    /**
     * @JsonIgnore bedeutet:
     * Dieses Feld soll nicht als JSON ausgegeben werden.
     *
     * Das ist beim Passwort wichtig,
     * damit es nie versehentlich an das Frontend geschickt wird.
     */
    @JsonIgnore
    private String password;

    /**
     * Rollen bzw. Berechtigungen des Benutzers.
     */
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Baut aus dem eigenen User-Modell
     * ein UserDetailsImpl-Objekt für Spring Security.
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                (long) user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

    /**
     * Gibt die Rollen des Benutzers zurück.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Gibt die Benutzer-ID zurück.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gibt den Benutzernamen zurück.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Gibt das Passwort zurück.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Diese Methoden sind Teil des UserDetails-Interfaces.
     *
     * Für dieses Projekt werden Benutzerkonten
     * immer als aktiv und gültig behandelt.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Vergleicht zwei Benutzerobjekte anhand ihrer ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}