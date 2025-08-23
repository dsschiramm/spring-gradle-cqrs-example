package company.system.utils.models.errors;

import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Instant timestamp;
    private List<FieldValidationError> fieldErrors;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public ErrorResponse(String code, String message, List<FieldValidationError> fieldErrors) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
        this.fieldErrors = fieldErrors;
    }
}
