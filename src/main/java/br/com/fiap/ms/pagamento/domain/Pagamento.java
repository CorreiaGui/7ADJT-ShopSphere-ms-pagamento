package br.com.fiap.ms.pagamento.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    private UUID id;

    private UUID pedidoId;

    private Integer formaPagamento;

    private String numeroCartaoCredito;

    private BigDecimal valor;

    private String solicitacaoPagamentoExternoId;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataUltimaAlteracao;
}
