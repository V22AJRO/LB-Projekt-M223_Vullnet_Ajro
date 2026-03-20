# LB-Projekt-M223_Vullnet_Ajro

## Projektübersicht
Dieses Projekt ist eine Multiuser-Webapplikation aus Modul 223 auf Basis von React, Spring Boot und MySQL.

Die Anwendung zeigt Länder und Alpha-Daten an. Eingeloggte Benutzer können Änderungsanträge erfassen. Administratoren können diese Anträge prüfen, genehmigen oder ablehnen.

## Technologien
- React
- Spring Boot
- MySQL
- JWT
- Maven
- Vite

## Voraussetzungen
- Java
- Maven
- Node.js / npm
- MySQL
- leere Datenbank `m223_country_explorer`

## Datenbank
Es genügt, die leere Datenbank `m223_country_explorer` anzulegen.  
Die Tabellen sowie die Basisdaten werden beim Start des Backends automatisch erzeugt.

## Backend starten
```bash
cd Backend/Country_Explorer_Backend
mvn spring-boot:run