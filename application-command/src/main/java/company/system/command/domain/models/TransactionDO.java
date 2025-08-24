package company.system.command.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TransactionDO {

    private final UUID operationId;
    private final Instant data;
    private final BigDecimal valor;
    private final Long cardholderId;
}
