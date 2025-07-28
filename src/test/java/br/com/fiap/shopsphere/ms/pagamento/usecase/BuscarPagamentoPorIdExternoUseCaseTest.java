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

public class BuscarPagamentoPorIdExternoUseCaseTest {

    private PagamentoGateway pagamentoGateway;
    private BuscarPagamentoPorIdExternoUseCase useCase;

    @BeforeEach
    void setup() {
        pagamentoGateway = mock(PagamentoGateway.class);
        useCase = new BuscarPagamentoPorIdExternoUseCase(pagamentoGateway);
    }

    @Test
    void deveRetornarPagamentoQuandoIdExternoExistir() {
        // Arrange
        UUID idExterno = UUID.randomUUID();
        Pagamento pagamento = Pagamento.builder()
                .id(UUID.randomUUID())
                .pedidoId(UUID.randomUUID())
                .formaPagamento(1)
                .numeroCartaoCredito("1234567890123456")
                .valor(BigDecimal.TEN)
                .solicitacaoPagamentoExternoId(idExterno)
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        when(pagamentoGateway.buscarPorIdExterno(idExterno)).thenReturn(Optional.of(pagamento));

        // Act
        Optional<Pagamento> resultado = useCase.buscarPorIdExterno(idExterno);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(pagamento, resultado.get());
        verify(pagamentoGateway).buscarPorIdExterno(idExterno);
    }

    @Test
    void deveRetornarOptionalVazioQuandoIdExternoNaoExistir() {
        // Arrange
        UUID idExterno = UUID.randomUUID();
        when(pagamentoGateway.buscarPorIdExterno(idExterno)).thenReturn(Optional.empty());

        // Act
        Optional<Pagamento> resultado = useCase.buscarPorIdExterno(idExterno);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(pagamentoGateway).buscarPorIdExterno(idExterno);
    }
}
