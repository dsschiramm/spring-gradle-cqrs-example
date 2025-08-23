package company.system.utils.models.errors;

import lombok.Getter;

@Getter
public class FieldValidationError {

    private String field;
    private Object rejectedValue; // cuidado com valores sensíveis — opcionalmente remova
    private String message;
    private String code; // código da violação (ex: NotNull, Size, Pattern, ...)

    public FieldValidationError(String field, Object rejectedValue, String message, String code) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
        this.code = code;
    }
}
