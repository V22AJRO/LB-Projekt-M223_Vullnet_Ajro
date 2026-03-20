package ch.wiss.countryexplorer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ch.wiss.countryexplorer.controller.ChangeRequestController;
import ch.wiss.countryexplorer.dto.ChangeRequestCreateRequest;
import ch.wiss.countryexplorer.model.ChangeRequest;
import ch.wiss.countryexplorer.model.Country;
import ch.wiss.countryexplorer.repository.ChangeRequestRepository;
import ch.wiss.countryexplorer.repository.CountryRepository;

/**
 * Testet den Änderungsantrags-Controller isoliert.
 *
 * Es wird geprüft:
 * - ob ein eingeloggter Benutzer einen Antrag korrekt erstellt
 * - ob ein Admin einen Antrag genehmigen und den Wert übernehmen kann
 */
@ExtendWith(MockitoExtension.class)
public class ChangeRequestControllerTest {

    @Mock
    private ChangeRequestRepository changeRequestRepository;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private ChangeRequestController changeRequestController;

    @Test
    void createStoresOpenChangeRequestForLoggedInUser() {
        ChangeRequestCreateRequest request = new ChangeRequestCreateRequest();
        request.setCountryId(1L);
        request.setFieldName("population");
        request.setNewValue("999");

        Country country = new Country("CHE", "Switzerland", "Bern", 9051029L, "Guy Parmelin", null);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "user1",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
        when(changeRequestRepository.save(any(ChangeRequest.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> response = changeRequestController.create(request, authentication);

        assertEquals("population", response.get("fieldName"));
        assertEquals("999", response.get("newValue"));
        assertEquals("OFFEN", response.get("status"));
        assertEquals("user1", response.get("requestedBy"));

        verify(countryRepository).findById(1L);
        verify(changeRequestRepository).save(any(ChangeRequest.class));
    }

    @Test
    void approveUpdatesCountryAndDeletesRequest() {
        Country country = new Country("CHE", "Switzerland", "Bern", 100L, "Guy Parmelin", null);

        ChangeRequest changeRequest = new ChangeRequest();
        changeRequest.setFieldName("population");
        changeRequest.setNewValue("200");
        changeRequest.setStatus("OFFEN");
        changeRequest.setRequestedBy("user1");
        changeRequest.setCountry(country);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "admin",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        when(changeRequestRepository.findById(10L)).thenReturn(Optional.of(changeRequest));
        when(countryRepository.save(any(Country.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> response = changeRequestController.approve(10L, authentication);

        assertEquals(200L, country.getPopulation());
        assertEquals("GENEHMIGT", response.get("status"));
        assertNotNull(response);
        assertTrue(response.containsKey("fieldName"));

        verify(countryRepository).save(country);
        verify(changeRequestRepository).delete(changeRequest);
    }
}