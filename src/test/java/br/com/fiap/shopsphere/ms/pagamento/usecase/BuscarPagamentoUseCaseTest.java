package br.com.fiap.shopsphere.ms.pagamento.usecase;

import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.PagamentoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarPagamentoUseCaseTest {

    private PagamentoGateway pagamentoGateway;
    private BuscarPagamentoUseCase useCase;

    private UUID pagamentoId;
    private Pagamento pagamento;

    @BeforeEach
    void setup() {
        pagamentoGateway = mock(PagamentoGateway.class);
        useCase = new BuscarPagamentoUseCase(pagamentoGateway);

        pagamentoId = UUID.randomUUID();
        pagamento = Pagamento.builder()
                .id(pagamentoId)
                .pedidoId(UUID.randomUUID())
                .formaPagamento(1)
                .numeroCartaoCredito("1234567890123456")
                .valor(BigDecimal.TEN)
                .solicitacaoPagamentoExternoId("sol456")
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();
    }

    @Test
    void deveBuscarPagamentoPorIdComSucesso() {
        when(pagamentoGateway.buscarPorId(pagamentoId)).thenReturn(Optional.of(pagamento));

        Optional<Pagamento> result = useCase.buscarPorId(pagamentoId);

        assertTrue(result.isPresent());
        assertEquals(pagamentoId, result.get().getId());
        verify(pagamentoGateway).buscarPorId(pagamentoId);
    }

    @Test
    void deveRetornarOptionalVazioQuandoPagamentoNaoForEncontrado() {
        when(pagamentoGateway.buscarPorId(pagamentoId)).thenReturn(Optional.empty());

        Optional<Pagamento> result = useCase.buscarPorId(pagamentoId);

        assertFalse(result.isPresent());
        verify(pagamentoGateway).buscarPorId(pagamentoId);
    }
}