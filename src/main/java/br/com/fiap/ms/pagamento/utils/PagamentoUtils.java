package br.com.fiap.ms.pagamento.utils;

import br.com.fiap.ms.pagamento.controller.json.PagamentoJson;
import br.com.fiap.ms.pagamento.domain.Pagamento;
import br.com.fiap.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;

public class PagamentoUtils {

    private PagamentoUtils() {}

    public static Pagamento convertToPagamento(PagamentoEntity pagamentoEntity){
        return Pagamento.builder()
                .id(pagamentoEntity.getId())
                .pedidoId(pagamentoEntity.getPedidoId())
                .formaPagamento(pagamentoEntity.getFormaPagamento())
                .numeroCartaoCredito(pagamentoEntity.getNumeroCartaoCredito())
                .valor(pagamentoEntity.getValor())
                .dataCriacao(pagamentoEntity.getDataCriacao())
                .dataUltimaAlteracao(pagamentoEntity.getDataUltimaAlteracao())
            .build();
    }

    public static PagamentoJson convertToPagamentoJson(Pagamento pagamento){
        return new PagamentoJson(
                pagamento.getId(),
                pagamento.getPedidoId(),
                pagamento.getFormaPagamento(),
                pagamento.getNumeroCartaoCredito(),
                pagamento.getValor(),
                pagamento.getDataCriacao(),
                pagamento.getDataUltimaAlteracao()
                );
    }

}
