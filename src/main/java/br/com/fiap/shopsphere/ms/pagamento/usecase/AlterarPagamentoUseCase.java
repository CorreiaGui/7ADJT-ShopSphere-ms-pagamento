package br.com.fiap.shopsphere.ms.pagamento.usecase;

import br.com.fiap.shopsphere.ms.pagamento.controller.json.PagamentoBodyRequestJson;
import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.exception.RecursoNaoEncontradoException;
import br.com.fiap.shopsphere.ms.pagamento.gateway.PagamentoGateway;
import br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.fiap.shopsphere.ms.pagamento.utils.PagamentoUtils.*;

@AllArgsConstructor
@Service
public class AlterarPagamentoUseCase {

    private final PagamentoGateway gateway;

    public void alterarPagamento(PagamentoBodyRequestJson json, UUID id) {
        Pagamento existente = gateway.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento com ID " + id + " não encontrado."));
        PagamentoEntity atualizado = convertToPagamentoEntity(json, existente);
        gateway.alterarPagamento(atualizado);
    }

}
