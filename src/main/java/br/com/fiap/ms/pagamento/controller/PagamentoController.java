package br.com.fiap.ms.pagamento.controller;

import br.com.fiap.ms.pagamento.controller.json.PagamentoJson;
import br.com.fiap.ms.pagamento.domain.Pagamento;
import br.com.fiap.ms.pagamento.usecase.BuscarPagamentoUseCase;
import br.com.fiap.ms.pagamento.utils.PagamentoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static br.com.fiap.ms.pagamento.utils.PagamentoUtils.convertToPagamentoJson;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = PagamentoController.V1_PAGAMENTOS, produces = "application/json")
public class PagamentoController {

    public static final String V1_PAGAMENTOS = "/api/v1/pagamentos";

    private final BuscarPagamentoUseCase buscarPagamentoUseCase;

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<PagamentoJson>> buscarPagamentosPorPedido(@PathVariable("pedidoId") UUID pedidoId) {
        log.info("Buscando pagamentos do pedido: {}", pedidoId);
        List<Pagamento> pagamentos = buscarPagamentoUseCase.buscarPagamentosPorPedido(pedidoId);
        List<PagamentoJson> pagamentosJson = pagamentos.stream().map(PagamentoUtils::convertToPagamentoJson).collect(toList());
        log.info("Pagamentos por pedido encontrados: {}", pagamentosJson);
        return ok(pagamentosJson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoJson> buscarPagamentoPorId(@PathVariable("id") UUID id) {
        log.info("Buscando pagamento com ID: {}", id);
        Pagamento pagamento = buscarPagamentoUseCase.buscarPorId(id);
        if (pagamento == null) {
            log.error("Pagamento não encontrado com ID: {}", id);
            return notFound().build();
        }
        log.info("Pagamento encontrado: {}", pagamento);
        PagamentoJson pagamentoJson = convertToPagamentoJson(pagamento);
        log.info("pagamentoJson encontrado: {}", pagamentoJson);
        return ok(pagamentoJson);
    }

    @GetMapping
    public ResponseEntity<List<PagamentoJson>> buscarTodosPagamentos() {
        log.info("Buscando pagamentos");
        List<Pagamento> pagamentos = buscarPagamentoUseCase.buscarTodosPagamentos();
        List<PagamentoJson> pagamentosJson = pagamentos.stream().map(PagamentoUtils::convertToPagamentoJson).collect(toList());
        log.info("Pagamentos encontrados: {}", pagamentosJson);
        return ok(pagamentosJson);
    }

}
