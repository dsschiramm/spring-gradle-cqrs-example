package company.system.command.domain.requests;

import company.system.utils.utility.CPFCNPJ;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CardholderRequest(
        @NotBlank String fullName,
        @NotBlank @Size(max = 255) String email,
        @CPFCNPJ String document,
        @NotBlank String password
) {
}