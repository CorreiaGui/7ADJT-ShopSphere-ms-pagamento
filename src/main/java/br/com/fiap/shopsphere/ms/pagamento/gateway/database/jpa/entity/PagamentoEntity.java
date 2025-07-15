package br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.AUTO;
import static java.time.LocalDateTime.now;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pagamento", schema = "ms_pagamento")
public class PagamentoEntity {
    @Id
    private UUID id;

    @Column(name = "pedido_id", nullable = false)
    private UUID pedidoId;

    @Column(name = "forma_pagamento", nullable = false)
    private Integer formaPagamento;

    @Column(name = "numero_cartao_credito", nullable = true)
    private String numeroCartaoCredito;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "solicitacao_pagamento_externo_id", nullable = true)
    private String solicitacaoPagamentoExternoId;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao = now();

    @Column(name = "data_ultima_alteracao")
    private LocalDateTime dataUltimaAlteracao;
}
