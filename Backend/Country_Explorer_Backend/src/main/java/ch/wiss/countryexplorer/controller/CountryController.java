package ch.wiss.countryexplorer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ch.wiss.countryexplorer.model.Country;
import ch.wiss.countryexplorer.model.Region;
import ch.wiss.countryexplorer.repository.CountryRepository;
import ch.wiss.countryexplorer.repository.RegionRepository;
import jakarta.validation.Valid;

/**
 * Dieser Controller verwaltet alle Anfragen rund um Länder.
 * 
 * Hier kann man:
 * - Länder anzeigen (GET)
 * - neue Länder speichern (POST)
 * - bestehende Länder ändern (PUT)
 * 
 * Löschen (DELETE) ist in diesem Projekt nicht vorgesehen.
 */
@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;

    public CountryController(CountryRepository countryRepository,
                             RegionRepository regionRepository) {
        this.countryRepository = countryRepository;
        this.regionRepository = regionRepository;
    }

    /**
     * Gibt eine Liste aller Länder zurück.
     * 
     * Optional kann man filtern:
     * - nach Region (z.B. ?region=Europe)
     * - nach Name (z.B. ?name=sw)
     * 
     * Wenn kein Filter gesetzt ist,
     * werden alle Länder aus der Datenbank geladen.
     */
    @GetMapping
    public List<Country> getAll(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String name) {

        if (region != null && !region.isBlank() &&
            name != null && !name.isBlank()) {
            return countryRepository
                    .findByRegion_NameIgnoreCaseAndNameContainingIgnoreCase(region, name);
        }

        if (region != null && !region.isBlank()) {
            return countryRepository
                    .findByRegion_NameIgnoreCase(region);
        }

        if (name != null && !name.isBlank()) {
            return countryRepository
                    .findByNameContainingIgnoreCase(name);
        }

        return countryRepository.findAll();
    }

    /**
     * Gibt genau ein Land anhand seiner ID zurück.
     * 
     * Beispiel:
     * GET /countries/1
     * 
     * Wenn das Land existiert, wird es zurückgegeben.
     * Wenn nicht, wird eine Fehlermeldung ausgelöst.
     */
    @GetMapping("/{id}")
    public Country getById(@PathVariable Long id) {

        return countryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Country mit ID " + id + " wurde nicht gefunden."));
    }

    /**
     * Erstellt ein neues Land.
     * 
     * Erwartet wird ein JSON Body, z.B.:
     * {
     *   "code": "CHE",
     *   "name": "Switzerland",
     *   "region": { "id": 1 }
     * }
     * 
     * Wichtig:
     * - Die Region muss bereits existieren.
     * - Die Region-ID wird verwendet, um das Land korrekt zu verknüpfen.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Country create(@Valid @RequestBody Country country) {

        if (country.getRegion() == null ||
            country.getRegion().getId() == null) {
            throw new RuntimeException("Region muss angegeben werden.");
        }

        Region region = regionRepository.findById(country.getRegion().getId())
                .orElseThrow(() ->
                        new RuntimeException("Region wurde nicht gefunden."));

        country.setRegion(region);

        return countryRepository.save(country);
    }

    /**
     * Aktualisiert ein bestehendes Land.
     * 
     * Beispiel:
     * PUT /countries/1
     * 
     * Die ID im Pfad bestimmt,
     * welches Land geändert wird.
     * Die übergebenen Werte überschreiben
     * die alten Werte in der Datenbank.
     */
    @PutMapping("/{id}")
    public Country update(@PathVariable Long id,
                          @Valid @RequestBody Country updatedCountry) {

        Country existing = countryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Country mit ID " + id + " wurde nicht gefunden."));

        existing.setCode(updatedCountry.getCode());
        existing.setName(updatedCountry.getName());

        if (updatedCountry.getRegion() != null &&
            updatedCountry.getRegion().getId() != null) {

            Region region = regionRepository.findById(updatedCountry.getRegion().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Region wurde nicht gefunden."));

            existing.setRegion(region);
        }

        return countryRepository.save(existing);
    }
}
