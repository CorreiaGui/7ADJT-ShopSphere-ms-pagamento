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

    private PagamentoRepository repository;
    private PagamentoJpaGateway gateway;

    @BeforeEach
    void setUp() {
        repository = mock(PagamentoRepository.class);
        gateway = new PagamentoJpaGateway(repository);
    }

    @Test
    void deveBuscarPagamentosPorPedido() {
        UUID pedidoId = UUID.randomUUID();
        PagamentoEntity entity = criarPagamentoEntity();
        when(repository.findAllByPedidoId(pedidoId)).thenReturn(List.of(entity));

        List<Pagamento> resultado = gateway.buscarPagamentosPorPedido(pedidoId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(entity.getPedidoId(), resultado.get(0).getPedidoId());
    }

    @Test
    void deveBuscarPagamentoPorId() {
        UUID id = UUID.randomUUID();
        PagamentoEntity entity = criarPagamentoEntity();
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        Optional<Pagamento> resultado = gateway.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(entity.getId(), resultado.get().getId());
    }

    @Test
    void deveBuscarPagamentoPorIdExterno() {
        UUID id = UUID.randomUUID();
        PagamentoEntity entity = criarPagamentoEntity();
        when(repository.findBySolicitacaoPagamentoExternoId(id)).thenReturn(Optional.of(entity));

        Optional<Pagamento> resultado = gateway.buscarPorIdExterno(id);

        assertTrue(resultado.isPresent());
        assertEquals(entity.getSolicitacaoPagamentoExternoId(), resultado.get().getSolicitacaoPagamentoExternoId());
    }

    @Test
    void deveBuscarTodosPagamentosPaginado() {
        PagamentoEntity entity = criarPagamentoEntity();
        Page<PagamentoEntity> page = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        List<Pagamento> resultado = gateway.buscarTodosPagamentos(0, 10);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void deveCriarPagamento() {
        PagamentoEntity entity = criarPagamentoEntity();
        when(repository.save(entity)).thenReturn(entity);

        PagamentoEntity resultado = gateway.criarPagamento(entity);

        assertNotNull(resultado);
        assertEquals(entity.getId(), resultado.getId());
    }

    @Test
    void deveAlterarPagamento() {
        PagamentoEntity entity = criarPagamentoEntity();
        when(repository.save(entity)).thenReturn(entity);

        PagamentoEntity resultado = gateway.alterarPagamento(entity);

        assertNotNull(resultado);
        assertEquals(entity.getId(), resultado.getId());
    }

    @Test
    void deveExcluirPagamentoComSucesso() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        assertDoesNotThrow(() -> gateway.excluirPagamento(id));
        verify(repository).deleteById(id);
    }

    @Test
    void deveLancarExcecaoAoExcluirPagamentoInexistente() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> gateway.excluirPagamento(id));
        assertEquals("Pagamento não encontrado - ID: " + id, ex.getMessage());
    }

    private PagamentoEntity criarPagamentoEntity() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        UUID externoId = UUID.randomUUID();
        LocalDateTime agora = LocalDateTime.now();

        return PagamentoEntity.builder()
                .id(id)
                .pedidoId(pedidoId)
                .formaPagamento(1)
                .numeroCartaoCredito("1234567890123456")
                .valor(BigDecimal.TEN)
                .solicitacaoPagamentoExternoId(externoId)
                .dataCriacao(agora)
                .dataUltimaAlteracao(agora)
                .build();
    }
}
