package br.com.fiap.shopsphere.ms.pagamento.controller;

import br.com.fiap.shopsphere.ms.pagamento.controller.json.PagamentoBodyRequestJson;
import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import br.com.fiap.shopsphere.ms.pagamento.usecase.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagamentoController.class)
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private BuscarPagamentoUseCase buscarPagamentoUseCase;
    @MockBean private BuscarPagamentoPorIdExternoUseCase buscarPagamentoPorIdExternoUseCase;
    @MockBean private BuscarPagamentosUseCase buscarPagamentosUseCase;
    @MockBean private BuscarPagamentosPorPedidoUseCase buscarPagamentosPorPedidoUseCase;
    @MockBean private CriarPagamentoUseCase criarPagamentoUseCase;
    @MockBean private AlterarPagamentoUseCase alterarPagamentoUseCase;
    @MockBean private ExcluirPagamentoUseCase excluirPagamentoUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID pagamentoId;
    private UUID pedidoId;
    private UUID pagamentoExternoId;

    @BeforeEach
    void setup() {
        pagamentoId = UUID.fromString("b984b5ec-4f87-493f-8023-06c357608baf");
        pedidoId = UUID.fromString("f2716fd9-d48e-4eb3-876f-d1b6f5e2f050");
        pagamentoExternoId = UUID.fromString("8801ed20-dc35-40e0-a900-c65c64f0c7cb");
    }

    private Pagamento createPagamento() {
        return Pagamento.builder()
                .id(pagamentoId)
                .pedidoId(pedidoId)
                .formaPagamento(1)
                .numeroCartaoCredito("1234567890123456")
                .valor(BigDecimal.TEN)
                .solicitacaoPagamentoExternoId(pagamentoExternoId)
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();
    }

    private PagamentoEntity createPagamentoEntity() {
        return PagamentoEntity.builder()
                .id(pagamentoId)
                .pedidoId(pedidoId)
                .formaPagamento(1)
                .numeroCartaoCredito("1234567890123456")
                .valor(BigDecimal.TEN)
                .solicitacaoPagamentoExternoId(pagamentoExternoId)
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();
    }

    @Test
    void deveBuscarPagamentoPorIdComSucesso() throws Exception {
        when(buscarPagamentoUseCase.buscarPorId(pagamentoId)).thenReturn(Optional.of(createPagamento()));

        mockMvc.perform(get("/api/v1/pagamentos/{id}", pagamentoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pagamentoId.toString()));
    }

    @Test
    void deveRetornarNotFoundQuandoPagamentoNaoForEncontradoPorId() throws Exception {
        when(buscarPagamentoUseCase.buscarPorId(pagamentoId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/pagamentos/{id}", pagamentoId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Pagamento com ID")));
    }

    @Test
    void deveBuscarPagamentoPorIdExternoComSucesso() throws Exception {
        when(buscarPagamentoPorIdExternoUseCase.buscarPorIdExterno(pagamentoExternoId))
                .thenReturn(Optional.of(createPagamento()));

        mockMvc.perform(get("/api/v1/pagamentos/pagamento-externo/{id}", pagamentoExternoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.solicitacaoPagamentoExternoId").value(pagamentoExternoId.toString()));
    }

    @Test
    void deveRetornarNotFoundQuandoPagamentoExternoNaoForEncontrado() throws Exception {
        when(buscarPagamentoPorIdExternoUseCase.buscarPorIdExterno(pagamentoExternoId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/pagamentos/pagamento-externo/{id}", pagamentoExternoId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Pagamento externo com ID")));
    }

    @Test
    void deveBuscarPagamentosPorPedido() throws Exception {
        when(buscarPagamentosPorPedidoUseCase.buscarPagamentosPorPedido(pedidoId)).thenReturn(List.of(createPagamento()));

        mockMvc.perform(get("/api/v1/pagamentos/pedido/{pedidoId}", pedidoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pedidoId").value(pedidoId.toString()));
    }

    @Test
    void deveRetornarNotFoundQuandoNenhumPagamentoPorPedido() throws Exception {
        when(buscarPagamentosPorPedidoUseCase.buscarPagamentosPorPedido(pedidoId)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/pagamentos/pedido/{pedidoId}", pedidoId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Nenhum pagamento encontrado")));
    }

    @Test
    void deveBuscarTodosPagamentos() throws Exception {
        when(buscarPagamentosUseCase.buscarTodosPagamentos(0, 10)).thenReturn(List.of(createPagamento()));

        mockMvc.perform(get("/api/v1/pagamentos?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(pagamentoId.toString()));
    }

    @Test
    void deveRetornarNotFoundQuandoNenhumPagamentoNaListaGeral() throws Exception {
        when(buscarPagamentosUseCase.buscarTodosPagamentos(0, 10)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/pagamentos?page=0&size=10"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Nenhum pagamento encontrado")));
    }

    @Test
    void deveCriarPagamentoComSucesso() throws Exception {
        PagamentoBodyRequestJson body = new PagamentoBodyRequestJson(pedidoId, 1, "1234567890123456", BigDecimal.TEN);
        PagamentoEntity entity = createPagamentoEntity();
        when(criarPagamentoUseCase.criarPagamento(any())).thenReturn(entity);

        mockMvc.perform(post("/api/v1/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(pagamentoId.toString())));
    }

    @Test
    void deveExcluirPagamentoComSucesso() throws Exception {
        doNothing().when(excluirPagamentoUseCase).excluirPagamento(pagamentoId);

        mockMvc.perform(delete("/api/v1/pagamentos/{id}", pagamentoId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoExcluirPagamentoInexistente() throws Exception {
        doThrow(new RuntimeException("Pagamento não encontrado")).when(excluirPagamentoUseCase).excluirPagamento(pagamentoId);

        mockMvc.perform(delete("/api/v1/pagamentos/{id}", pagamentoId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Pagamento não encontrado")));
    }

    @Test
    void deveAlterarPagamentoComSucesso() throws Exception {
        PagamentoBodyRequestJson body = new PagamentoBodyRequestJson(pedidoId, 1, "1234567890123456", BigDecimal.TEN);
        doNothing().when(alterarPagamentoUseCase).alterarPagamento(body, pagamentoId);

        mockMvc.perform(put("/api/v1/pagamentos/{id}", pagamentoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pagamento atualizado com sucesso")));
    }
}
