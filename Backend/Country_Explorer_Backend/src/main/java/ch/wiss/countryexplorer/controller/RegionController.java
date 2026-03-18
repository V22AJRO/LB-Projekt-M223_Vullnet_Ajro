package ch.wiss.countryexplorer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ch.wiss.countryexplorer.model.Region;
import ch.wiss.countryexplorer.repository.RegionRepository;
import jakarta.validation.Valid;

/**
 * Dieser Controller verwaltet die Regionen.
 * 
 * Eine Region ist z.B. "Europe" oder "Asia".
 * 
 * Hier kann man:
 * - alle Regionen anzeigen (GET)
 * - eine neue Region erstellen (POST)
 */
@RestController
@RequestMapping("/regions")
public class RegionController {

    private final RegionRepository regionRepository;

    public RegionController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    /**
     * Gibt alle gespeicherten Regionen zurück.
     * 
     * Beispiel:
     * GET /regions
     * 
     * Antwort:
     * Eine Liste mit allen Regionen aus der Datenbank.
     */
    @GetMapping
    public List<Region> getAll() {
        return regionRepository.findAll();
    }

    /**
     * Gibt eine einzelne Region anhand der ID zurück.
     * 
     * Beispiel:
     * GET /regions/1
     * 
     * Wenn die ID existiert, wird die Region zurückgegeben.
     * Wenn sie nicht existiert, gibt Spring automatisch einen Fehler zurück.
     */
    @GetMapping("/{id}")
    public Region getById(@PathVariable Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region not found"));
    }

    /**
     * Erstellt eine neue Region.
     * 
     * Erwartet wird ein JSON Body wie:
     * {
     *   "name": "Europe"
     * }
     * 
     * Der Name darf nicht leer sein.
     * Nach dem Speichern wird die neue Region
     * mit ID zurückgegeben.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Region create(@Valid @RequestBody Region region) {
        return regionRepository.save(region);
    }
}
