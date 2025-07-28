package br.com.fiap.shopsphere.ms.pagamento.controller;

import br.com.fiap.shopsphere.ms.pagamento.controller.json.PagamentoBodyRequestJson;
import br.com.fiap.shopsphere.ms.pagamento.controller.json.PagamentoJson;
import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.exception.RecursoNaoEncontradoException;
import br.com.fiap.shopsphere.ms.pagamento.usecase.*;
import br.com.fiap.shopsphere.ms.pagamento.utils.PagamentoUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static br.com.fiap.shopsphere.ms.pagamento.utils.PagamentoUtils.convertToPagamentoJson;
import static br.com.fiap.shopsphere.ms.pagamento.utils.PagamentoConstants.V1_PAGAMENTOS;
import static br.com.fiap.shopsphere.ms.pagamento.utils.PagamentoConstants.PRODUCES;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = V1_PAGAMENTOS, produces = PRODUCES)
public class PagamentoController {

    private final BuscarPagamentoUseCase buscarPagamentoUseCase;

    private final BuscarPagamentoPorIdExternoUseCase buscarPagamentoPorIdExternoUseCase;

    private final BuscarPagamentosUseCase buscarPagamentosUseCase;

    private final BuscarPagamentosPorPedidoUseCase buscarPagamentosPorPedidoUseCase;

    private final CriarPagamentoUseCase criarPagamentoUseCase;

    private final AlterarPagamentoUseCase alterarPagamentoUseCase;

    private final ExcluirPagamentoUseCase excluirPagamentoUseCase;

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<PagamentoJson>> buscarPagamentosPorPedido(@PathVariable("pedidoId") UUID pedidoId) {
        log.info("Buscando pagamentos do pedido: {}", pedidoId);
        List<Pagamento> pagamentos = buscarPagamentosPorPedidoUseCase.buscarPagamentosPorPedido(pedidoId);
        if (pagamentos.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum pagamento encontrado para o pedido ID " + pedidoId);
        }
        List<PagamentoJson> pagamentosJson = pagamentos.stream().map(PagamentoUtils::convertToPagamentoJson).collect(toList());
        log.info("Pagamentos por pedido encontrados: {}", pagamentosJson);
        return ok(pagamentosJson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoJson> buscarPagamentoPorId(@PathVariable("id") UUID id) {
        log.info("Buscando pagamento com ID: {}", id);
        Pagamento pagamento = buscarPagamentoUseCase.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento com ID " + id + " não encontrado."));
        log.info("Pagamento encontrado: {}", pagamento);
        PagamentoJson pagamentoJson = convertToPagamentoJson(pagamento);
        log.info("pagamentoJson encontrado: {}", pagamentoJson);
        return ok(pagamentoJson);
    }

    @GetMapping("/pagamento-externo/{id}")
    public ResponseEntity<PagamentoJson> buscarPagamentoPorIdExterno(@PathVariable("id") UUID id) {
        log.info("Buscando pagamento com ID externo: {}", id);
        Pagamento pagamento = buscarPagamentoPorIdExternoUseCase.buscarPorIdExterno(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento externo com ID " + id + " não encontrado."));
        log.info("Pagamento externo encontrado: {}", pagamento);
        PagamentoJson pagamentoJson = convertToPagamentoJson(pagamento);
        log.info("pagamentoJson encontrado: {}", pagamentoJson);
        return ok(pagamentoJson);
    }

    @GetMapping
    public ResponseEntity<List<PagamentoJson>> buscarTodosPagamentos(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "size", defaultValue = "10") int size) {

        log.info("GET | {} | Iniciado busca de pagamentos com paginacao | page: {} size: {} ", V1_PAGAMENTOS, page, size);
        List<Pagamento> pagamentos = buscarPagamentosUseCase.buscarTodosPagamentos(page, size);
        if (pagamentos.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum pagamento encontrado.");
        }
        log.info("GET | {} | Finalizada busca de pagamentos com paginacao | page: {} size: {} produtos: {}", V1_PAGAMENTOS, page, size, pagamentos);
        List<PagamentoJson> pagamentosJson = pagamentos.stream().map(PagamentoUtils::convertToPagamentoJson).toList();
        return ok(pagamentosJson);
    }

    @PostMapping
    public ResponseEntity<UUID> criarPagamento(@RequestBody @Valid PagamentoBodyRequestJson pagamentoBodyRequestJson) {
        log.info("POST | {} | Iniciado criação de pagamento | ", V1_PAGAMENTOS);
        var pagamento = criarPagamentoUseCase.criarPagamento(pagamentoBodyRequestJson);
        log.info("POST | {} | Finalizada criação de pagamento | ", V1_PAGAMENTOS);
        return status(CREATED).body(pagamento.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPagamento(@PathVariable("id") UUID id) {
        log.info("DELETE | {} | Iniciado exclusão de Pagamento | id: {}", V1_PAGAMENTOS, id);
        try {
            excluirPagamentoUseCase.excluirPagamento(id);
            log.info("DELETE | {} | Pagamento excluído com sucesso | Id: {}", V1_PAGAMENTOS, id);
            return status(NO_CONTENT).body("Pagamento excluído com sucesso!");
        } catch (RuntimeException e) {
            log.error("DELETE | {} | Erro ao excluir Pagamento | Id: {} | Erro: {}", V1_PAGAMENTOS, id, e.getMessage());
            return status(NOT_FOUND).body("Pagamento não encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> alterarPagamento(@PathVariable("id") UUID id, @RequestBody @Valid PagamentoBodyRequestJson pagamentoBodyRequestJson) {
        log.info("PUT | {} | Iniciado alterarPagamento | id: {}", V1_PAGAMENTOS, id);
        alterarPagamentoUseCase.alterarPagamento(pagamentoBodyRequestJson, id);
        log.info("PUT | {} | Finalizado alterarPagamento", V1_PAGAMENTOS);
        return ok("Pagamento atualizado com sucesso!");
    }
}
