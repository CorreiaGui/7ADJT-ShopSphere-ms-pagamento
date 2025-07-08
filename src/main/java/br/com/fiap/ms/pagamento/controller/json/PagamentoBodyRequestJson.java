package br.com.fiap.ms.pagamento.controller.json;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record PagamentoBodyRequestJson(
        @NotNull(message = "O ID do pedido é obrigatório")
        UUID pedidoId,

        @NotNull(message = "A forma de pagamento é obrigatória")
        @Min(value = 1, message = "Forma de pagamento inválida")
        Integer formaPagamento,

        @Size(max = 30, message = "Número do cartão deve ter no máximo 30 caracteres")
        String numeroCartaoCredito,

        @NotNull(message = "O valor do pagamento é obrigatório")
        @DecimalMin(value = "0.01", inclusive = true, message = "O valor deve ser maior que zero")
        BigDecimal valor
) {}
