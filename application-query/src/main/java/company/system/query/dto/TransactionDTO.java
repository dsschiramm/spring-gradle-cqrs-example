package company.system.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.TupleTransformer;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO implements TupleTransformer<TransactionDTO> {

    private Long id;
    private Date data;
    private BigDecimal valor;
    private Long cardholderId;

    @Override
    public TransactionDTO transformTuple(Object[] tuple, String[] aliases) {
        int i = 0;
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId((Long) tuple[i++]);
        transactionDTO.setData((Date) tuple[i++]);
        transactionDTO.setValor((BigDecimal) tuple[i++]);
        transactionDTO.setCardholderId((Long) tuple[i]);
        return transactionDTO;
    }
}
