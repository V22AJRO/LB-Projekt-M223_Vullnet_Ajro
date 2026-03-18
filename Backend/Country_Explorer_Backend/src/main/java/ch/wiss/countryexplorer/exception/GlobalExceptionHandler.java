package ch.wiss.countryexplorer.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

/**
 * Sammelt typische Validierungs-Fehler an einem Ort.
 *
 * Idee:
 * - Wenn der User falsche Daten schickt, soll er eine klare Liste von Fehlermeldungen bekommen.
 * - Statt einem langen Technik-Fehler (Stacktrace).
 *
 * Beispiele:
 * - GET /countries/abc  -> "id muss eine Zahl sein"
 * - POST /countries mit leerem code -> "code is required"
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Fehler für Parameter in der URL.
     *
     * Betrifft meistens:
     * - @RequestParam  (z.B. ?region=)
     * - @PathVariable  (z.B. /countries/123)
     *
     * Ergebnis:
     * - HTTP 400 (Bad Request)
     * - Body: Liste mit kurzen Texten, was falsch ist
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleConstraintViolation(
            ConstraintViolationException ex) {

        return ResponseEntity.badRequest().body(
                ex.getConstraintViolations()
                  .stream()
                  .map(v -> v.getMessage())
                  .toList()
        );
    }

    /**
     * Fehler im JSON Body (RequestBody).
     *
     * Typisch bei POST/PUT:
     * - Pflichtfeld fehlt
     * - Text zu kurz/zu lang
     *
     * Ergebnis:
     * - HTTP 400 (Bad Request)
     * - Body: Liste der Feld-Fehlermeldungen
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidation(
            MethodArgumentNotValidException ex) {

        return ResponseEntity.badRequest().body(
                ex.getBindingResult()
                  .getFieldErrors()
                  .stream()
                  .map(e -> e.getDefaultMessage())
                  .toList()
        );
    }
}
