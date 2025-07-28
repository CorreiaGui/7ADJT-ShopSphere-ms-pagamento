package br.com.fiap.shopsphere.ms.pagamento.usecase;

import br.com.fiap.shopsphere.ms.pagamento.controller.json.PagamentoBodyRequestJson;
import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.PagamentoGateway;
import br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import br.com.fiap.shopsphere.ms.pagamento.gateway.external.PagamentoExternoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CriarPagamentoUseCaseTest {

    private PagamentoGateway pagamentoGateway;
    private PagamentoExternoGateway pagamentoExternoGateway;
    private CriarPagamentoUseCase useCase;

    private UUID pedidoId;
    private UUID pagamentoExternoId;
    private PagamentoBodyRequestJson bodyRequest;
    private PagamentoEntity entityEsperada;

    @BeforeEach
    void setup() {
        pagamentoGateway = mock(PagamentoGateway.class);
        pagamentoExternoGateway = mock(PagamentoExternoGateway.class);
        useCase = new CriarPagamentoUseCase(pagamentoGateway, pagamentoExternoGateway);

        pedidoId = UUID.randomUUID();
        pagamentoExternoId = UUID.randomUUID();

        bodyRequest = new PagamentoBodyRequestJson(
                pedidoId,
                1,
                "1234567890123456",
                BigDecimal.valueOf(99.99)
        );

        entityEsperada = PagamentoEntity.builder()
                .id(UUID.randomUUID())
                .pedidoId(pedidoId)
                .formaPagamento(1)
                .numeroCartaoCredito("1234567890123456")
                .valor(BigDecimal.valueOf(99.99))
                .solicitacaoPagamentoExternoId(pagamentoExternoId)
                .build();
    }

    @Test
    void deveCriarPagamentoComSucesso() {
        when(pagamentoExternoGateway.iniciarPagamentoExterno(any(Pagamento.class)))
                .thenReturn(pagamentoExternoId); // usa o UUID já criado no setup

        when(pagamentoGateway.criarPagamento(any(PagamentoEntity.class)))
                .thenReturn(entityEsperada);

        PagamentoEntity result = useCase.criarPagamento(bodyRequest);

        assertNotNull(result);
        assertEquals(entityEsperada.getId(), result.getId());
        assertEquals(pagamentoExternoId, result.getSolicitacaoPagamentoExternoId());
        verify(pagamentoGateway).criarPagamento(any(PagamentoEntity.class));
        verify(pagamentoExternoGateway).iniciarPagamentoExterno(any(Pagamento.class));
    }
}