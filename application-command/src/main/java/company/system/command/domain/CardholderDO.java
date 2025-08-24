package company.system.command.domain;

import company.system.utils.utility.CPFCNPJ;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardholderDO {

    @NotBlank
    @Column(name = "full_name")
    private String fullName;

    @Size(max = 255)
    private String email;

    @CPFCNPJ
    private String document;

    @NotBlank
    private String password;
}
