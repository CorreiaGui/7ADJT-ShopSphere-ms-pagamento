package br.com.fiap.shopsphere.ms.pagamento.gateway.external;

import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;

import java.util.UUID;

public interface PagamentoExternoGateway {
    UUID iniciarPagamentoExterno(Pagamento pagamento);
}
