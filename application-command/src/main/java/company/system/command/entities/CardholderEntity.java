package company.system.command.entities;


import company.system.command.domain.CardholderDO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "cardholder")
@NoArgsConstructor
public class CardholderEntity {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String cpf;

    public CardholderEntity(CardholderDO cardholderDO) {
        this.fullName = cardholderDO.getFullName();
        this.email = cardholderDO.getEmail();
        this.cpf = cardholderDO.getCpf();
    }

    public CardholderDO toDO() {
        return new CardholderDO(this.fullName, this.email, this.cpf);
    }
}
