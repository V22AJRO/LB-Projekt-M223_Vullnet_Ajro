package ch.wiss.countryexplorer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import ch.wiss.countryexplorer.controller.AuthController;
import ch.wiss.countryexplorer.dto.JwtResponse;
import ch.wiss.countryexplorer.dto.LoginRequest;
import ch.wiss.countryexplorer.repository.RoleRepository;
import ch.wiss.countryexplorer.repository.UserRepository;
import ch.wiss.countryexplorer.security.JwtUtils;
import ch.wiss.countryexplorer.security.UserDetailsImpl;

/**
 * Testet den Login-Controller isoliert.
 *
 * Hier wird geprüft, ob bei gültigen Logindaten
 * eine erfolgreiche Antwort mit JWT zurückgegeben wird.
 */
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthController authController;

    @Test
    void authenticateUserReturnsJwtResponseForValidLogin() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("123456");

        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L,
                "admin",
                "123456",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateToken("admin")).thenReturn("test-token");

        ResponseEntity<?> response = authController.authenticateUser(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(JwtResponse.class, response.getBody());

        JwtResponse body = (JwtResponse) response.getBody();

        assertEquals("test-token", body.getToken());
        assertEquals("admin", body.getUsername());
        assertTrue(body.getRoles().contains("ROLE_ADMIN"));

        verify(authenticationManager).authenticate(any());
        verify(jwtUtils).generateToken("admin");
    }
}