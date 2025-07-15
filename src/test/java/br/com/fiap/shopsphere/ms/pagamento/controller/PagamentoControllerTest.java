package br.com.fiap.shopsphere.ms.pagamento.controller;

import br.com.fiap.shopsphere.ms.pagamento.controller.json.PagamentoBodyRequestJson;
import br.com.fiap.shopsphere.ms.pagamento.controller.json.PagamentoJson;
import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.exception.RecursoNaoEncontradoException;
import br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import br.com.fiap.shopsphere.ms.pagamento.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class PagamentoControllerTest {

    @InjectMocks
    private PagamentoController controller;

    @Mock private BuscarPagamentoUseCase buscarPagamentoUseCase;
    @Mock private BuscarPagamentosUseCase buscarPagamentosUseCase;
    @Mock private BuscarPagamentosPorPedidoUseCase buscarPagamentosPorPedidoUseCase;
    @Mock private CriarPagamentoUseCase criarPagamentoUseCase;
    @Mock private AlterarPagamentoUseCase alterarPagamentoUseCase;
    @Mock private ExcluirPagamentoUseCase excluirPagamentoUseCase;

    private UUID pagamentoId;
    private UUID pedidoId;
    private Pagamento pagamento;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        pagamentoId = UUID.randomUUID();
        pedidoId = UUID.randomUUID();
        pagamento = Pagamento.builder()
                .id(pagamentoId)
                .pedidoId(pedidoId)
                .formaPagamento(1)
                .numeroCartaoCredito("1234567890123456")
                .valor(BigDecimal.TEN)
                .solicitacaoPagamentoExternoId("ext123")
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();
    }

    @Test
    void deveBuscarPagamentoPorIdComSucesso() {
        when(buscarPagamentoUseCase.buscarPorId(pagamentoId)).thenReturn(Optional.of(pagamento));

        ResponseEntity<PagamentoJson> response = controller.buscarPagamentoPorId(pagamentoId);

        assertEquals(OK, response.getStatusCode());
        assertEquals(pagamento.getId(), response.getBody().id());
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoForEncontrado() {
        when(buscarPagamentoUseCase.buscarPorId(pagamentoId)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> controller.buscarPagamentoPorId(pagamentoId));
    }

    @Test
    void deveBuscarTodosPagamentosComSucesso() {
        when(buscarPagamentosUseCase.buscarTodosPagamentos(0, 10)).thenReturn(List.of(pagamento));

        ResponseEntity<List<PagamentoJson>> response = controller.buscarTodosPagamentos(0, 10);

        assertEquals(OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void deveLancarExcecaoQuandoNaoHouverPagamentos() {
        when(buscarPagamentosUseCase.buscarTodosPagamentos(0, 10)).thenReturn(Collections.emptyList());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> controller.buscarTodosPagamentos(0, 10));
    }

    @Test
    void deveBuscarPagamentosPorPedidoComSucesso() {
        when(buscarPagamentosPorPedidoUseCase.buscarPagamentosPorPedido(pedidoId)).thenReturn(List.of(pagamento));

        ResponseEntity<List<PagamentoJson>> response = controller.buscarPagamentosPorPedido(pedidoId);

        assertEquals(OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void deveLancarExcecaoQuandoNaoHouverPagamentosParaPedido() {
        when(buscarPagamentosPorPedidoUseCase.buscarPagamentosPorPedido(pedidoId)).thenReturn(Collections.emptyList());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> controller.buscarPagamentosPorPedido(pedidoId));
    }

    @Test
    void deveCriarPagamentoComSucesso() {
        PagamentoBodyRequestJson body = new PagamentoBodyRequestJson(pedidoId, 1, "1234567890123456", BigDecimal.TEN);
        PagamentoEntity entity = PagamentoEntity.builder().id(pagamentoId).build();

        when(criarPagamentoUseCase.criarPagamento(body)).thenReturn(entity);

        ResponseEntity<UUID> response = controller.criarPagamento(body);

        assertEquals(CREATED, response.getStatusCode());
        assertEquals(pagamentoId, response.getBody());
    }

    @Test
    void deveAlterarPagamentoComSucesso() {
        PagamentoBodyRequestJson body = new PagamentoBodyRequestJson(pedidoId, 1, "1234567890123456", BigDecimal.TEN);

        ResponseEntity<String> response = controller.alterarPagamento(pagamentoId, body);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Pagamento atualizado com sucesso!", response.getBody());
        verify(alterarPagamentoUseCase).alterarPagamento(body, pagamentoId);
    }

    @Test
    void deveExcluirPagamentoComSucesso() {
        ResponseEntity<String> response = controller.excluirPagamento(pagamentoId);

        assertEquals(NO_CONTENT, response.getStatusCode());
        assertEquals("Pagamento excluído com sucesso!", response.getBody());
        verify(excluirPagamentoUseCase).excluirPagamento(pagamentoId);
    }

    @Test
    void deveRetornarErroAoExcluirPagamentoInexistente() {
        doThrow(new RuntimeException("Pagamento não encontrado")).when(excluirPagamentoUseCase).excluirPagamento(pagamentoId);

        ResponseEntity<String> response = controller.excluirPagamento(pagamentoId);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Pagamento não encontrado", response.getBody());
    }
}
