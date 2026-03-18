package ch.wiss.countryexplorer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dieser Controller dient nur als einfacher Test.
 * 
 * Mit /ping kann schnell geprüft werden,
 * ob die API läuft.
 * 
 * Wenn "ok" zurückkommt,
 * ist der Server gestartet und erreichbar.
 */
@RestController
public class PingController {

    /**
     * Test-Endpunkt der API.
     * 
     * Beispiel:
     * GET /ping
     * 
     * Erwartete Antwort:
     * ok
     */
    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
