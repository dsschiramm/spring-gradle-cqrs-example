package company.system.command.domain.requests;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequest(
        @NotNull String documentOrigin,
        @NotNull String documentDestination,
        @NotNull BigDecimal valor
) {
}