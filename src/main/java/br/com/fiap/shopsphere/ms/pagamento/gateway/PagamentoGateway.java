package br.com.fiap.shopsphere.ms.pagamento.gateway;

import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagamentoGateway {
    List<Pagamento> buscarPagamentosPorPedido(UUID pedidoId);

    Optional<Pagamento> buscarPorId(UUID ID);

    Optional<Pagamento> buscarPorIdExterno(UUID ID);

    List<Pagamento> buscarTodosPagamentos(int page, int size);

    PagamentoEntity criarPagamento(PagamentoEntity entity);

    void excluirPagamento(UUID id);

    PagamentoEntity alterarPagamento(PagamentoEntity id);
}
