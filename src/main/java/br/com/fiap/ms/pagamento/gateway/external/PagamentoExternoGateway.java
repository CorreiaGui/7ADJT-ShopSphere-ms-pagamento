package br.com.fiap.ms.pagamento.gateway.external;

import br.com.fiap.ms.pagamento.domain.Pagamento;

public interface PagamentoExternoGateway {
    String iniciarPagamentoExterno(Pagamento pagamento);
}
