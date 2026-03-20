package ch.wiss.countryexplorer.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

/**
 * Diese Klasse beschreibt einen Änderungsantrag.
 *
 * Ein Änderungsantrag speichert:
 * - welches Feld geändert werden soll
 * - welchen neuen Wert der Benutzer vorschlägt
 * - den aktuellen Status
 * - den Benutzer, der den Antrag erstellt hat
 * - das Erstellungsdatum
 * - das betroffene Land
 */
@Entity
@Table(name = "change_requests")
public class ChangeRequest {

    /**
     * Eindeutige ID des Änderungsantrags.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name des Feldes, das geändert werden soll.
     * Beispiel: population oder president
     */
    @Column(nullable = false)
    private String fieldName;

    /**
     * Neuer vorgeschlagener Wert.
     */
    @Column(nullable = false, length = 255)
    private String newValue;

    /**
     * Status des Antrags.
     * Beispiel: OFFEN, GENEHMIGT, ABGELEHNT
     */
    @Column(nullable = false, length = 30)
    private String status;

    /**
     * Username der Person, die den Antrag erstellt hat.
     */
    @Column(nullable = false, length = 255)
    private String requestedBy;

    /**
     * Zeitpunkt der Erstellung.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Das Land, auf das sich der Antrag bezieht.
     *
     * @JsonIgnoreProperties verhindert,
     * dass beim JSON-Rückgabewert unnötige verschachtelte Daten
     * oder Endlosschleifen entstehen.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    @JsonIgnoreProperties({"region"})
    private Country country;

    public ChangeRequest() {
    }

    public ChangeRequest(String fieldName, String newValue, String status, String requestedBy, LocalDateTime createdAt, Country country) {
        this.fieldName = fieldName;
        this.newValue = newValue;
        this.status = status;
        this.requestedBy = requestedBy;
        this.createdAt = createdAt;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}