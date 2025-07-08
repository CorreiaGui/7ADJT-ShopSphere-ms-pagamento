package br.com.fiap.ms.pagamento.usecase;

import br.com.fiap.ms.pagamento.controller.json.PagamentoBodyRequestJson;
import br.com.fiap.ms.pagamento.gateway.PagamentoGateway;
import br.com.fiap.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.fiap.ms.pagamento.utils.PagamentoUtils.convertToPagamentoEntity;

@Service
public class CriarPagamentoUseCase {@Autowired
    private PagamentoGateway pagamentoGateway;

    public PagamentoEntity criarPagamento(PagamentoBodyRequestJson json) {
        PagamentoEntity entity = convertToPagamentoEntity(json);
        return pagamentoGateway.criarPagamento(entity);
    }
}
