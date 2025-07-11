package br.com.fiap.ms.pagamento.usecase;

import br.com.fiap.ms.pagamento.controller.json.PagamentoBodyRequestJson;
import br.com.fiap.ms.pagamento.domain.Pagamento;
import br.com.fiap.ms.pagamento.exception.RecursoNaoEncontradoException;
import br.com.fiap.ms.pagamento.gateway.PagamentoGateway;
import br.com.fiap.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.fiap.ms.pagamento.utils.PagamentoUtils.*;

@Service
public class AlterarPagamentoUseCase {

    @Autowired
    private PagamentoGateway gateway;

    public void alterarPagamento(PagamentoBodyRequestJson json, UUID id) {
        Pagamento existente = gateway.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento com ID " + id + " não encontrado."));
        PagamentoEntity atualizado = convertToPagamentoEntity(json, existente);
        gateway.alterarPagamento(atualizado);
    }

}
