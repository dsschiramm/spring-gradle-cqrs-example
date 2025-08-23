package company.system.utils.models.output;

import lombok.Getter;

@Getter
public class FieldValidationDTO {

    private final String field;
    private final Object rejectedValue; // cuidado com valores sensíveis — opcionalmente remova
    private final String message;
    private final String code; // código da violação (ex: NotNull, Size, Pattern, ...)

    public FieldValidationDTO(String field, Object rejectedValue, String message, String code) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
        this.code = code;
    }
}
