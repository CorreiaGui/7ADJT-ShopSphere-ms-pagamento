package br.com.fiap.shopsphere.ms.pagamento.usecase;

import br.com.fiap.shopsphere.ms.pagamento.controller.json.PagamentoBodyRequestJson;
import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.exception.RecursoNaoEncontradoException;
import br.com.fiap.shopsphere.ms.pagamento.gateway.PagamentoGateway;
import br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlterarPagamentoUseCaseTest {

    private PagamentoGateway gateway;
    private AlterarPagamentoUseCase useCase;

    private UUID pagamentoId;
    private UUID pagamentoExternoId;
    private UUID pedidoId;
    private Pagamento pagamentoExistente;
    private PagamentoBodyRequestJson jsonRequest;

    @BeforeEach
    void setup() {
        gateway = mock(PagamentoGateway.class);
        useCase = new AlterarPagamentoUseCase(gateway); // ✔️ corrigido

        pagamentoId = UUID.randomUUID();
        pagamentoExternoId = UUID.randomUUID();
        pedidoId = UUID.randomUUID();

        pagamentoExistente = Pagamento.builder()
                .id(pagamentoId)
                .pedidoId(pedidoId)
                .formaPagamento(1)
                .numeroCartaoCredito("1234567890123456")
                .valor(BigDecimal.TEN)
                .solicitacaoPagamentoExternoId(pagamentoExternoId)
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        jsonRequest = new PagamentoBodyRequestJson(
                pedidoId,
                2,
                "9999888877776666",
                BigDecimal.valueOf(99.99)
        );
    }

    @Test
    void deveAlterarPagamentoComSucesso() {
        when(gateway.buscarPorId(pagamentoId)).thenReturn(Optional.of(pagamentoExistente));

        useCase.alterarPagamento(jsonRequest, pagamentoId);

        verify(gateway).alterarPagamento(any(PagamentoEntity.class));
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoForEncontrado() {
        when(gateway.buscarPorId(pagamentoId)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException ex = assertThrows(RecursoNaoEncontradoException.class,
                () -> useCase.alterarPagamento(jsonRequest, pagamentoId));

        assertEquals("Pagamento com ID " + pagamentoId + " não encontrado.", ex.getMessage());
        verify(gateway, never()).alterarPagamento(any());
    }
}