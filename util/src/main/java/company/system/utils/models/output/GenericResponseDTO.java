package company.system.utils.models.output;

import lombok.Getter;

/**
 * Poderia ser retornado um correlationId para acompanhar em fluxos
 */
@Getter
public class GenericResponseDTO {

    private final Long correlationId;
    private final Long createdId;
    private final String message;

    public GenericResponseDTO(Long createdId, String message) {
        this.correlationId = null;
        this.createdId = createdId;
        this.message = message;
    }

    public GenericResponseDTO(Long correlationId, Long createdId, String message) {
        this.correlationId = correlationId;
        this.createdId = createdId;
        this.message = message;
    }
}
