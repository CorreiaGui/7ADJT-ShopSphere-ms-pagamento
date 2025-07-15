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

class BuscarPagamentosUseCaseTest {

    private PagamentoGateway pagamentoGateway;
    private BuscarPagamentosUseCase useCase;

    private Pagamento pagamento;

    @BeforeEach
    void setup() {
        pagamentoGateway = mock(PagamentoGateway.class);
        useCase = new BuscarPagamentosUseCase(pagamentoGateway);

        pagamento = Pagamento.builder()
                .id(UUID.randomUUID())
                .pedidoId(UUID.randomUUID())
                .formaPagamento(1)
                .numeroCartaoCredito("1234567890123456")
                .valor(BigDecimal.TEN)
                .solicitacaoPagamentoExternoId("sol123")
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();
    }

    @Test
    void deveRetornarTodosPagamentosComSucesso() {
        when(pagamentoGateway.buscarTodosPagamentos(0, 10)).thenReturn(List.of(pagamento));

        List<Pagamento> result = useCase.buscarTodosPagamentos(0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pagamentoGateway).buscarTodosPagamentos(0, 10);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverPagamentos() {
        when(pagamentoGateway.buscarTodosPagamentos(0, 10)).thenReturn(List.of());

        List<Pagamento> result = useCase.buscarTodosPagamentos(0, 10);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(pagamentoGateway).buscarTodosPagamentos(0, 10);
    }
}