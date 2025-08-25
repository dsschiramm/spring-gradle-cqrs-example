package company.system.utils.models.output;

import lombok.Getter;

import java.util.UUID;

/**
 * Poderia ser retornado um correlationId para acompanhar em fluxos
 */
@Getter
public class GenericResponseDTO {

    private final UUID operationId;
    private final Long createdId;
    private final String message;

    public GenericResponseDTO(String message) {
        this.operationId = null;
        this.createdId = null;
        this.message = message;
    }

    public GenericResponseDTO(Long createdId, String message) {
        this.operationId = null;
        this.createdId = createdId;
        this.message = message;
    }

    public GenericResponseDTO(UUID correlationId, String message) {
        this.operationId = correlationId;
        this.createdId = null;
        this.message = message;
    }
}
