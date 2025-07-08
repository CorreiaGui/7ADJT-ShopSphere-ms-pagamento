package br.com.fiap.ms.pagamento.usecase;

import br.com.fiap.ms.pagamento.gateway.PagamentoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExcluirPagamentoUseCase {

    @Autowired
    private PagamentoGateway gateway;

    public void excluirPagamento(UUID id) {
        gateway.excluirPagamento(id);
    }

}
