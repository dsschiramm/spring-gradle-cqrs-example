package company.system.utils.models.output;

import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class ErrorDTO {

    private final String code;
    private final String message;
    private final Instant timestamp;
    private List<FieldValidationDTO> fieldErrors;

    public ErrorDTO(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public ErrorDTO(String code, String message, List<FieldValidationDTO> fieldErrors) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
        this.fieldErrors = fieldErrors;
    }
}
