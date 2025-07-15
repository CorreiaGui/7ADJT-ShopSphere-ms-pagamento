package br.com.fiap.shopsphere.ms.pagamento.usecase;

import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.PagamentoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarPagamentosPorPedidoUseCaseTest {

    private PagamentoGateway pagamentoGateway;
    private BuscarPagamentosPorPedidoUseCase useCase;

    private UUID pedidoId;
    private Pagamento pagamento;

    @BeforeEach
    void setup() {
        pagamentoGateway = mock(PagamentoGateway.class);
        useCase = new BuscarPagamentosPorPedidoUseCase(pagamentoGateway);

        pedidoId = UUID.randomUUID();
        pagamento = Pagamento.builder()
                .id(UUID.randomUUID())
                .pedidoId(pedidoId)
                .formaPagamento(1)
                .numeroCartaoCredito("1234567890123456")
                .valor(BigDecimal.valueOf(100))
                .solicitacaoPagamentoExternoId("externo123")
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();
    }

    @Test
    void deveRetornarPagamentosPorPedidoComSucesso() {
        when(pagamentoGateway.buscarPagamentosPorPedido(pedidoId)).thenReturn(List.of(pagamento));

        List<Pagamento> result = useCase.buscarPagamentosPorPedido(pedidoId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(pedidoId, result.get(0).getPedidoId());
        verify(pagamentoGateway).buscarPagamentosPorPedido(pedidoId);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremPagamentos() {
        when(pagamentoGateway.buscarPagamentosPorPedido(pedidoId)).thenReturn(List.of());

        List<Pagamento> result = useCase.buscarPagamentosPorPedido(pedidoId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(pagamentoGateway).buscarPagamentosPorPedido(pedidoId);
    }
}