package company.system.command.domain.ports.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {

    private String email;
    private String template;
    private List<String> argumentos;

    public NotificationDTO(String email, @NotNull BigDecimal valor, String template) {
        this.email = email;
        this.template = template;
        this.argumentos = List.of(String.valueOf(valor));
    }
}
