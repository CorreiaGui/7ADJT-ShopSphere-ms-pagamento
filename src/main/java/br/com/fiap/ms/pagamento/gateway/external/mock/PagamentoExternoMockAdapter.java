package br.com.fiap.ms.pagamento.gateway.external.mock;

import br.com.fiap.ms.pagamento.domain.Pagamento;
import br.com.fiap.ms.pagamento.gateway.external.PagamentoExternoGateway;
import org.springframework.stereotype.Component;

@Component
public class PagamentoExternoMockAdapter implements PagamentoExternoGateway {

    @Override
    public String iniciarPagamentoExterno(Pagamento pagamento) {
        // Simula o processamento de um pagamento externo
        return "mock-transaction-id-" + pagamento.getId();
    }

}
