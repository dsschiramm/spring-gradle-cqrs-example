package company.system.command.infrastructure.entities;


import company.system.command.domain.enums.CardholderTypeEnum;
import company.system.command.domain.models.CardholderDO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "cardholder")
@NoArgsConstructor
public class CardholderEntity {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation_id", nullable = false)
    private UUID operationId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String document;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardholderTypeEnum type;

    public CardholderEntity(CardholderDO cardholderDO) {
        this.operationId = cardholderDO.getOperationId();
        this.fullName = cardholderDO.getFullName();
        this.email = cardholderDO.getEmail();
        this.document = cardholderDO.getDocument();
        this.password = cardholderDO.getPassword();
        this.type = cardholderDO.getType();
    }

    public CardholderDO toDO() {
        return new CardholderDO(operationId, fullName, email, document, password, type, id);
    }
}
