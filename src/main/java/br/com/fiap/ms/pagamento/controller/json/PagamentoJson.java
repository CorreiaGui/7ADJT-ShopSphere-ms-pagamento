package br.com.fiap.ms.pagamento.controller.json;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoJson(
    UUID id,
    UUID pedidoId,
    Integer formaPagamento,
    String numeroCartaoCredito,
    BigDecimal valor,
    String solicitacaoPagamentoExternoId,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}
