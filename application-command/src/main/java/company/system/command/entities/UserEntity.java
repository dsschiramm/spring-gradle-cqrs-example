package company.system.command.entities;


import company.system.command.exceptions.user.InvalidCpfException;
import company.system.utils.utility.CPFUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
