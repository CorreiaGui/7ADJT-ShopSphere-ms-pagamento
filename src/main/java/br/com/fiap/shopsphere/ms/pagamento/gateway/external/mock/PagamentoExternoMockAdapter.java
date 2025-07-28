package br.com.fiap.shopsphere.ms.pagamento.gateway.external.mock;

import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.external.PagamentoExternoGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PagamentoExternoMockAdapter implements PagamentoExternoGateway {

    @Override
    public UUID iniciarPagamentoExterno(Pagamento pagamento) {
        // Simula o processamento de um pagamento externo
        return UUID.randomUUID();
    }

}
