package br.com.fiap.shopsphere.ms.pagamento.gateway.external;

import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;

public interface PagamentoExternoGateway {
    String iniciarPagamentoExterno(Pagamento pagamento);
}
