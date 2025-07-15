package br.com.fiap.shopsphere.ms.pagamento.usecase;

import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.PagamentoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarPagamentosUseCase {

    private final PagamentoGateway pagamentoGateway;

    public List<Pagamento> buscarTodosPagamentos(int page, int size) {
        return pagamentoGateway.buscarTodosPagamentos(page, size);
    }

}
