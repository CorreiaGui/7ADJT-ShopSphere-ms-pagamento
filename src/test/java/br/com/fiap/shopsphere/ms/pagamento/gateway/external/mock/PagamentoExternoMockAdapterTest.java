package br.com.fiap.shopsphere.ms.pagamento.gateway.external.mock;

import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoExternoMockAdapterTest {

    @Test
    void deveGerarIdDeTransacaoMockComSucesso() {
        Pagamento pagamento = Pagamento.builder()
                .id(UUID.randomUUID())
                .pedidoId(UUID.randomUUID())
                .formaPagamento(1)
                .numeroCartaoCredito("1234567812345678")
                .valor(BigDecimal.valueOf(150.0))
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        PagamentoExternoMockAdapter adapter = new PagamentoExternoMockAdapter();

        String resultado = adapter.iniciarPagamentoExterno(pagamento);

        assertNotNull(resultado);
        assertTrue(resultado.startsWith("mock-transaction-id-"));
        assertTrue(resultado.contains(pagamento.getId().toString()));
    }
}
