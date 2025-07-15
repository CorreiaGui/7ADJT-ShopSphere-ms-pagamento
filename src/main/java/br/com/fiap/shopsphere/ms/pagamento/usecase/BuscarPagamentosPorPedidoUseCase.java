package br.com.fiap.shopsphere.ms.pagamento.usecase;

import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.PagamentoGateway;
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
