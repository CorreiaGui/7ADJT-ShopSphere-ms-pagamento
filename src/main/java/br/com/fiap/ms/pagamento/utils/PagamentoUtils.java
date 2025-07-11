package br.com.fiap.ms.pagamento.utils;

import br.com.fiap.ms.pagamento.controller.json.PagamentoBodyRequestJson;
import br.com.fiap.ms.pagamento.controller.json.PagamentoJson;
import br.com.fiap.ms.pagamento.domain.Pagamento;
import br.com.fiap.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;

import static java.time.LocalDateTime.now;

public class PagamentoUtils {

    private PagamentoUtils() {}

    public static Pagamento convertToPagamento(PagamentoEntity pagamentoEntity){
        return Pagamento.builder()
                .id(pagamentoEntity.getId())
                .pedidoId(pagamentoEntity.getPedidoId())
                .formaPagamento(pagamentoEntity.getFormaPagamento())
                .numeroCartaoCredito(pagamentoEntity.getNumeroCartaoCredito())
                .valor(pagamentoEntity.getValor())
                .solicitacaoPagamentoExternoId(pagamentoEntity.getSolicitacaoPagamentoExternoId())
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
                pagamento.getSolicitacaoPagamentoExternoId(),
                pagamento.getDataCriacao(),
                pagamento.getDataUltimaAlteracao()
                );
    }

    public static PagamentoEntity convertToPagamentoEntity(PagamentoBodyRequestJson json){
        return PagamentoEntity.builder()
                .pedidoId(json.pedidoId())
                .formaPagamento(json.formaPagamento())
                .numeroCartaoCredito(json.numeroCartaoCredito())
                .valor(json.valor())
                .dataCriacao(now())
                .build();
    }

    public static PagamentoEntity convertToPagamentoEntity(PagamentoBodyRequestJson json, Pagamento existente){
        return PagamentoEntity.builder()
                .id(existente.getId())
                .pedidoId(json.pedidoId())
                .formaPagamento(json.formaPagamento())
                .numeroCartaoCredito(json.numeroCartaoCredito())
                .valor(json.valor())
                .dataCriacao(existente.getDataCriacao())
                .dataUltimaAlteracao(now())
                .build();
    }

    public static PagamentoEntity convertToPagamentoEntity(Pagamento pagamento){
        return PagamentoEntity.builder()
                .id(pagamento.getId())
                .pedidoId(pagamento.getPedidoId())
                .formaPagamento(pagamento.getFormaPagamento())
                .numeroCartaoCredito(pagamento.getNumeroCartaoCredito())
                .valor(pagamento.getValor())
                .solicitacaoPagamentoExternoId(pagamento.getSolicitacaoPagamentoExternoId())
                .dataCriacao(pagamento.getDataCriacao())
                .dataUltimaAlteracao(pagamento.getDataUltimaAlteracao())
                .build();
    }

}
