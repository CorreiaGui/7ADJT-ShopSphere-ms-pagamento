package br.com.fiap.shopsphere.ms.pagamento.usecase;

import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.PagamentoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarPagamentoPorIdExternoUseCase {
    private final PagamentoGateway pagamentoGateway;

    public Optional<Pagamento> buscarPorIdExterno(UUID id) {
        return pagamentoGateway.buscarPorIdExterno(id);
    }
}
