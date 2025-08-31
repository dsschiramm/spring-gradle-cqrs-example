package company.system.command.infrastructure.entities;

import company.system.command.domain.models.TransactionDO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(name = "transaction")
@NoArgsConstructor
public class TransactionEntity {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation_id", nullable = false)
    private UUID operationId;

    @Column(nullable = false)
    private Instant data;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "cardholder_id", nullable = false)
    private Long cardholderId;

    public TransactionEntity(TransactionDO transactionDO) {
        this.operationId = transactionDO.getOperationId();
        this.data = transactionDO.getData();
        this.valor = transactionDO.getValor();
        this.cardholderId = transactionDO.getCardholderId();
    }

    public TransactionDO toDO() {
        return new TransactionDO(operationId, data, valor, cardholderId);
    }
}
