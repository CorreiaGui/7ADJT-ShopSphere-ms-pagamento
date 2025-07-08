package br.com.fiap.ms.pagamento.gateway;

import br.com.fiap.ms.pagamento.domain.Pagamento;
import br.com.fiap.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagamentoGateway {
    List<Pagamento> buscarPagamentosPorPedido(UUID pedidoId);

    Optional<Pagamento> buscarPorId(UUID ID);

    List<Pagamento> buscarTodosPagamentos(int page, int size);

    PagamentoEntity criarPagamento(PagamentoEntity entity);

    void excluirPagamento(UUID id);

    PagamentoEntity alterarPagamento(PagamentoEntity id);
}
