package ch.wiss.countryexplorer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ch.wiss.countryexplorer.model.Language;
import ch.wiss.countryexplorer.repository.LanguageRepository;
import jakarta.validation.Valid;

/**
 * Dieser Controller verwaltet alle Sprachen (Languages).
 * 
 * Hier können Sprachen gelesen und neu erstellt werden.
 * 
 * Es gibt:
 * - GET  → alle Sprachen anzeigen
 * - POST → neue Sprache speichern
 */
@RestController
@RequestMapping("/languages")
public class LanguageController {

    private final LanguageRepository languageRepository;

    public LanguageController(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    /**
     * Gibt alle gespeicherten Sprachen zurück.
     * 
     * Beispiel:
     * GET /languages
     * 
     * Antwort:
     * Eine Liste mit allen Sprach-Objekten.
     */
    @GetMapping
    public List<Language> getAll() {
        return languageRepository.findAll();
    }

    /**
     * Erstellt eine neue Sprache.
     * 
     * Erwartet wird ein JSON Body, zum Beispiel:
     * {
     *   "code": "de",
     *   "name": "German"
     * }
     * 
     * Code und Name dürfen nicht leer sein.
     * Nach dem Speichern wird die neue Sprache
     * mit ID zurückgegeben.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Language create(@Valid @RequestBody Language language) {
        return languageRepository.save(language);
    }
}
