package br.com.fiap.ms.pagamento.usecase;

import br.com.fiap.ms.pagamento.domain.Pagamento;
import br.com.fiap.ms.pagamento.gateway.PagamentoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarPagamentoUseCase {

    private final PagamentoGateway pagamentoGateway;

    public Optional<Pagamento> buscarPorId(UUID id) {
        return pagamentoGateway.buscarPorId(id);
    }

}
