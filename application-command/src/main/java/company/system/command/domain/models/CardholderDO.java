package company.system.command.domain.models;

import company.system.command.domain.enums.CardholderTypeEnum;
import company.system.command.domain.requests.CardholderRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CardholderDO {

    private final UUID operationId;
    private final String fullName;
    private final String email;
    private final String document;
    private final String password;
    private final CardholderTypeEnum type;
    private Boolean active;
    private Long id;

    public CardholderDO(CardholderRequest cardholderRequest) {
        this.operationId = UUID.randomUUID();
        this.fullName = cardholderRequest.fullName();
        this.email = cardholderRequest.email();
        this.document = cardholderRequest.document();
        this.password = cardholderRequest.password();
        this.active = true;

        if (this.document.length() == 14) {
            type = CardholderTypeEnum.MERCHANT;
        } else {
            type = CardholderTypeEnum.PERSON;
        }
    }

    public void desativar() {
        this.active = false;
    }
}
