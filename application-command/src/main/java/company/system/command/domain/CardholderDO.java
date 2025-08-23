package company.system.command.domain;

import company.system.command.exceptions.user.InvalidCpfException;
import company.system.utils.utility.CPFUtil;
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

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    public void validate() throws InvalidCpfException {
        this.cpf = CPFUtil.clean(this.cpf);

        if (!CPFUtil.isValid(this.cpf)) {
            throw new InvalidCpfException();
        }
    }
}
