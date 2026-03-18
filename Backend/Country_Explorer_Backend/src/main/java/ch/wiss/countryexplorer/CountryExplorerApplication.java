package ch.wiss.countryexplorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Startpunkt der Anwendung.
 *
 * Wenn du das Projekt startest, läuft genau diese main()-Methode los.
 * Danach startet Spring Boot automatisch:
 * - den Webserver (Tomcat)
 * - die Controller (/countries, /regions, /languages, /ping)
 * - die Verbindung zur Datenbank
 */
@SpringBootApplication
public class CountryExplorerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountryExplorerApplication.class, args);
    }
}
