package br.com.fiap.ms.pagamento.usecase;

import br.com.fiap.ms.pagamento.domain.Pagamento;
import br.com.fiap.ms.pagamento.gateway.PagamentoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarPagamentosPorPedidoUseCase {

    private final PagamentoGateway pagamentoGateway;

    public List<Pagamento> buscarPagamentosPorPedido(UUID pedidoId) {
        return pagamentoGateway.buscarPagamentosPorPedido(pedidoId);
    }

}
