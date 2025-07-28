package br.com.fiap.shopsphere.ms.pagamento.usecase;

import br.com.fiap.shopsphere.ms.pagamento.controller.json.PagamentoBodyRequestJson;
import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.PagamentoGateway;
import br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import br.com.fiap.shopsphere.ms.pagamento.gateway.external.PagamentoExternoGateway;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.fiap.shopsphere.ms.pagamento.utils.PagamentoUtils.convertToPagamentoEntity;

@Service
public class CriarPagamentoUseCase {

    private final PagamentoGateway pagamentoGateway;
    private final PagamentoExternoGateway pagamentoExternoGateway;

    public CriarPagamentoUseCase(PagamentoGateway pagamentoGateway,
                                 PagamentoExternoGateway pagamentoExternoGateway) {
        this.pagamentoGateway = pagamentoGateway;
        this.pagamentoExternoGateway = pagamentoExternoGateway;
    }

    public PagamentoEntity criarPagamento(PagamentoBodyRequestJson json) {
        Pagamento pagamento = Pagamento.builder()
                .id(UUID.randomUUID())
                .pedidoId(json.pedidoId())
                .formaPagamento(json.formaPagamento())
                .numeroCartaoCredito(json.numeroCartaoCredito())
                .valor(json.valor())
                .dataCriacao(LocalDateTime.now())
                .build();

        UUID idExterno = pagamentoExternoGateway.iniciarPagamentoExterno(pagamento);
        pagamento.setSolicitacaoPagamentoExternoId(idExterno);

        PagamentoEntity entity = convertToPagamentoEntity(pagamento);
        return pagamentoGateway.criarPagamento(entity);
    }
}
