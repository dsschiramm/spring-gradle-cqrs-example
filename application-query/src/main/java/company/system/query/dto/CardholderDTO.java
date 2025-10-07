package company.system.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.TupleTransformer;

@Getter
@Setter
@NoArgsConstructor
public class CardholderDTO implements TupleTransformer<CardholderDTO> {

    private Long id;
    private String fullName;
    private String email;
    private String document;
    private String type;
    private Boolean active;

    @Override
    public CardholderDTO transformTuple(Object[] tuple, String[] aliases) {
        int i = 0;
        CardholderDTO cardholderDTO = new CardholderDTO();
        cardholderDTO.setId((Long) tuple[i++]);
        cardholderDTO.setFullName((String) tuple[i++]);
        cardholderDTO.setEmail((String) tuple[i++]);
        cardholderDTO.setDocument((String) tuple[i++]);
        cardholderDTO.setType((String) tuple[i++]);
        cardholderDTO.setActive((Boolean) tuple[i]);
        return cardholderDTO;
    }
}
