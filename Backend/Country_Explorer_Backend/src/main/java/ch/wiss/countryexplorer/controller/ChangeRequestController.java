package ch.wiss.countryexplorer.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import ch.wiss.countryexplorer.dto.ChangeRequestCreateRequest;
import ch.wiss.countryexplorer.model.ChangeRequest;
import ch.wiss.countryexplorer.model.Country;
import ch.wiss.countryexplorer.repository.ChangeRequestRepository;
import ch.wiss.countryexplorer.repository.CountryRepository;

@RestController
@RequestMapping("/change-requests")
public class ChangeRequestController {

    private final ChangeRequestRepository changeRequestRepository;
    private final CountryRepository countryRepository;

    public ChangeRequestController(ChangeRequestRepository changeRequestRepository,
                                   CountryRepository countryRepository) {
        this.changeRequestRepository = changeRequestRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public List<Map<String, Object>> getAll(Authentication authentication) {
        ensureAdmin(authentication);

        return changeRequestRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> create(@RequestBody ChangeRequestCreateRequest request,
                                      Authentication authentication) {
        ensureLoggedIn(authentication);

        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country wurde nicht gefunden."));

        ChangeRequest changeRequest = new ChangeRequest();
        changeRequest.setCountry(country);
        changeRequest.setFieldName(request.getFieldName());
        changeRequest.setNewValue(request.getNewValue());
        changeRequest.setStatus("OFFEN");
        changeRequest.setRequestedBy(authentication.getName());
        changeRequest.setCreatedAt(LocalDateTime.now());

        ChangeRequest savedChangeRequest = changeRequestRepository.save(changeRequest);

        return toResponse(savedChangeRequest);
    }

    @PutMapping("/{id}/approve")
    public Map<String, Object> approve(@PathVariable Long id, Authentication authentication) {
        ensureAdmin(authentication);

        ChangeRequest changeRequest = changeRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Änderungsantrag wurde nicht gefunden."));

        Country country = changeRequest.getCountry();

        if ("population".equals(changeRequest.getFieldName())) {
            country.setPopulation(Long.valueOf(changeRequest.getNewValue()));
        }

        if ("president".equals(changeRequest.getFieldName())) {
            country.setPresident(changeRequest.getNewValue());
        }

        countryRepository.save(country);

        Map<String, Object> response = toResponse(changeRequest);
        response.put("status", "GENEHMIGT");

        changeRequestRepository.delete(changeRequest);

        return response;
    }

    @PutMapping("/{id}/reject")
    public Map<String, Object> reject(@PathVariable Long id, Authentication authentication) {
        ensureAdmin(authentication);

        ChangeRequest changeRequest = changeRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Änderungsantrag wurde nicht gefunden."));

        Map<String, Object> response = toResponse(changeRequest);
        response.put("status", "ABGELEHNT");

        changeRequestRepository.delete(changeRequest);

        return response;
    }

    private void ensureLoggedIn(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Nicht eingeloggt.");
        }
    }

    private void ensureAdmin(Authentication authentication) {
        ensureLoggedIn(authentication);

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new RuntimeException("Kein Admin-Zugriff.");
        }
    }

    private Map<String, Object> toResponse(ChangeRequest changeRequest) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", changeRequest.getId());
        response.put("fieldName", changeRequest.getFieldName());
        response.put("newValue", changeRequest.getNewValue());
        response.put("status", changeRequest.getStatus());
        response.put("requestedBy", changeRequest.getRequestedBy());
        response.put("createdAt", changeRequest.getCreatedAt());
        response.put("countryId", changeRequest.getCountry().getId());
        response.put("countryName", changeRequest.getCountry().getName());
        return response;
    }
}