package company.system.command.domain.models;

import company.system.command.domain.enums.CardholderTypeEnum;
import company.system.command.domain.requests.CardholderRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardholderDO {

    private String fullName;
    private String email;
    private String document;
    private String password;
    private CardholderTypeEnum type;

    public CardholderDO(CardholderRequest cardholderRequest) {
        this.fullName = cardholderRequest.fullName();
        this.email = cardholderRequest.email();
        this.document = cardholderRequest.document();
        this.password = cardholderRequest.password();

        if (this.document.length() == 14) {
            type = CardholderTypeEnum.MERCHANT;
        } else {
            type = CardholderTypeEnum.PERSON;
        }
    }
}
