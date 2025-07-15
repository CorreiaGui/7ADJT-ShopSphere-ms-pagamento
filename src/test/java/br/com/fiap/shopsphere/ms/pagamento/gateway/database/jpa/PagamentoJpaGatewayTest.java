package br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa;

import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoJpaGatewayTest {

    private PagamentoRepository pagamentoRepository;
    private PagamentoJpaGateway gateway;

    private UUID pagamentoId;
    private UUID pedidoId;
    private PagamentoEntity entity;

    @BeforeEach
    void setup() {
        pagamentoRepository = mock(PagamentoRepository.class);
        gateway = new PagamentoJpaGateway(pagamentoRepository);

        pagamentoId = UUID.randomUUID();
        pedidoId = UUID.randomUUID();

        entity = PagamentoEntity.builder()
                .id(pagamentoId)
                .pedidoId(pedidoId)
                .formaPagamento(1)
                .numeroCartaoCredito("1234567890123456")
                .valor(BigDecimal.TEN)
                .solicitacaoPagamentoExternoId("sol123")
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();
    }

    @Test
    void deveBuscarPagamentoPorIdComSucesso() {
        when(pagamentoRepository.findById(pagamentoId)).thenReturn(Optional.of(entity));

        Optional<Pagamento> result = gateway.buscarPorId(pagamentoId);

        assertTrue(result.isPresent());
        assertEquals(pagamentoId, result.get().getId());
    }

    @Test
    void deveRetornarOptionalVazioQuandoPagamentoNaoForEncontrado() {
        when(pagamentoRepository.findById(pagamentoId)).thenReturn(Optional.empty());

        Optional<Pagamento> result = gateway.buscarPorId(pagamentoId);

        assertTrue(result.isEmpty());
    }

    @Test
    void deveBuscarPagamentosPorPedidoComSucesso() {
        List<PagamentoEntity> entities = List.of(entity);
        when(pagamentoRepository.findAllByPedidoId(pedidoId)).thenReturn(entities);

        List<Pagamento> result = gateway.buscarPagamentosPorPedido(pedidoId);

        assertEquals(1, result.size());
        assertEquals(pedidoId, result.get(0).getPedidoId());
    }

    @Test
    void deveBuscarTodosPagamentosComSucesso() {
        Page<PagamentoEntity> page = new PageImpl<>(List.of(entity));
        when(pagamentoRepository.findAll(any(Pageable.class))).thenReturn(page);

        List<Pagamento> result = gateway.buscarTodosPagamentos(0, 10);

        assertEquals(1, result.size());
        assertEquals(pagamentoId, result.get(0).getId());
    }

    @Test
    void deveCriarPagamentoComSucesso() {
        when(pagamentoRepository.save(entity)).thenReturn(entity);

        PagamentoEntity result = gateway.criarPagamento(entity);

        assertNotNull(result);
        assertEquals(pagamentoId, result.getId());
    }

    @Test
    void deveAlterarPagamentoComSucesso() {
        when(pagamentoRepository.save(entity)).thenReturn(entity);

        PagamentoEntity result = gateway.alterarPagamento(entity);

        assertNotNull(result);
        assertEquals(pagamentoId, result.getId());
    }

    @Test
    void deveExcluirPagamentoComSucesso() {
        when(pagamentoRepository.existsById(pagamentoId)).thenReturn(true);

        gateway.excluirPagamento(pagamentoId);

        verify(pagamentoRepository).deleteById(pagamentoId);
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoExistirNaExclusao() {
        when(pagamentoRepository.existsById(pagamentoId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> gateway.excluirPagamento(pagamentoId));

        assertTrue(exception.getMessage().contains("Pagamento não encontrado"));
    }
}
