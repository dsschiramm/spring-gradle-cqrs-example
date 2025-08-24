package company.system.command.entities;


import company.system.command.domain.models.CardholderDO;
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

    @Column(name = "full_name")
    private String fullName;
    private String email;
    private String document;
    private String password;

    public CardholderEntity(CardholderDO cardholderDO) {
        this.fullName = cardholderDO.getFullName();
        this.email = cardholderDO.getEmail();
        this.document = cardholderDO.getDocument();
        this.password = cardholderDO.getPassword();
    }

    public CardholderDO toDO() {
        return new CardholderDO(this.fullName, this.email, this.document, this.password);
    }
}
