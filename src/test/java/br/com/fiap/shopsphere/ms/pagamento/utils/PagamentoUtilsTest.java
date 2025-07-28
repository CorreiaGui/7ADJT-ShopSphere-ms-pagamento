package br.com.fiap.shopsphere.ms.pagamento.utils;

import br.com.fiap.shopsphere.ms.pagamento.controller.json.PagamentoBodyRequestJson;
import br.com.fiap.shopsphere.ms.pagamento.controller.json.PagamentoJson;
import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoUtilsTest {

    @Test
    void deveConverterPagamentoEntityParaPagamento() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        UUID pagamentoExternoId = UUID.fromString("8b539e0d-c767-4aa2-87d7-046c64989bd9");
        LocalDateTime dataCriacao = LocalDateTime.now();
        LocalDateTime ultimaAlteracao = LocalDateTime.now();

        PagamentoEntity entity = PagamentoEntity.builder()
                .id(id)
                .pedidoId(pedidoId)
                .formaPagamento(1)
                .numeroCartaoCredito("1234")
                .valor(BigDecimal.TEN)
                .solicitacaoPagamentoExternoId(pagamentoExternoId)
                .dataCriacao(dataCriacao)
                .dataUltimaAlteracao(ultimaAlteracao)
                .build();

        Pagamento pagamento = PagamentoUtils.convertToPagamento(entity);

        assertNotNull(pagamento);
        assertEquals(id, pagamento.getId());
        assertEquals(pedidoId, pagamento.getPedidoId());
        assertEquals("1234", pagamento.getNumeroCartaoCredito());
        assertEquals(BigDecimal.TEN, pagamento.getValor());
        assertEquals(pagamentoExternoId, pagamento.getSolicitacaoPagamentoExternoId());
        assertEquals(dataCriacao, pagamento.getDataCriacao());
        assertEquals(ultimaAlteracao, pagamento.getDataUltimaAlteracao());
    }


    @Test
    void deveConverterPagamentoParaPagamentoJson() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        UUID pagamentoExternoId = UUID.fromString("0d58abd0-82fb-4707-ba87-31309a4bffcd");
        LocalDateTime criado = LocalDateTime.now();
        LocalDateTime alterado = LocalDateTime.now();

        Pagamento pagamento = Pagamento.builder()
                .id(id)
                .pedidoId(pedidoId)
                .formaPagamento(1)
                .numeroCartaoCredito("4321")
                .valor(BigDecimal.ONE)
                .solicitacaoPagamentoExternoId(pagamentoExternoId)
                .dataCriacao(criado)
                .dataUltimaAlteracao(alterado)
                .build();

        PagamentoJson json = PagamentoUtils.convertToPagamentoJson(pagamento);

        assertNotNull(json);
        assertEquals(id, json.id());
        assertEquals(pedidoId, json.pedidoId());
        assertEquals(1, json.formaPagamento());
        assertEquals("4321", json.numeroCartaoCredito());
        assertEquals(BigDecimal.ONE, json.valor());
        assertEquals(pagamentoExternoId, json.solicitacaoPagamentoExternoId());
        assertEquals(criado, json.dataCriacao());
        assertEquals(alterado, json.dataUltimaAlteracao());
    }

    @Test
    void deveConverterPagamentoBodyRequestJsonParaEntity() {
        UUID pedidoId = UUID.randomUUID();
        PagamentoBodyRequestJson body = new PagamentoBodyRequestJson(pedidoId, 2, "9999", BigDecimal.valueOf(55.55));

        PagamentoEntity entity = PagamentoUtils.convertToPagamentoEntity(body);

        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals(pedidoId, entity.getPedidoId());
        assertEquals(2, entity.getFormaPagamento());
        assertEquals("9999", entity.getNumeroCartaoCredito());
        assertEquals(BigDecimal.valueOf(55.55), entity.getValor());
        assertNotNull(entity.getDataCriacao());
        assertNull(entity.getDataUltimaAlteracao());
    }

    @Test
    void deveConverterPagamentoBodyRequestJsonEExistenteParaEntity() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        LocalDateTime dataCriacao = LocalDateTime.now().minusDays(1);

        Pagamento existente = Pagamento.builder()
                .id(id)
                .pedidoId(pedidoId)
                .formaPagamento(1)
                .numeroCartaoCredito("1234")
                .valor(BigDecimal.TEN)
                .dataCriacao(dataCriacao)
                .build();

        PagamentoBodyRequestJson body = new PagamentoBodyRequestJson(pedidoId, 3, "8888", BigDecimal.valueOf(88.88));

        PagamentoEntity entity = PagamentoUtils.convertToPagamentoEntity(body, existente);

        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(pedidoId, entity.getPedidoId());
        assertEquals(3, entity.getFormaPagamento());
        assertEquals("8888", entity.getNumeroCartaoCredito());
        assertEquals(BigDecimal.valueOf(88.88), entity.getValor());
        assertEquals(dataCriacao, entity.getDataCriacao());
        assertNotNull(entity.getDataUltimaAlteracao());
    }

    @Test
    void deveConverterPagamentoParaEntity() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        UUID pagamentoExternoId = UUID.fromString("8f4c4931-16c7-473f-bb55-e79687ee3071");
        LocalDateTime criado = LocalDateTime.now();
        LocalDateTime alterado = LocalDateTime.now();

        Pagamento pagamento = Pagamento.builder()
                .id(id)
                .pedidoId(pedidoId)
                .formaPagamento(4)
                .numeroCartaoCredito("7777")
                .valor(BigDecimal.valueOf(77.77))
                .solicitacaoPagamentoExternoId(pagamentoExternoId)
                .dataCriacao(criado)
                .dataUltimaAlteracao(alterado)
                .build();

        PagamentoEntity entity = PagamentoUtils.convertToPagamentoEntity(pagamento);

        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(pedidoId, entity.getPedidoId());
        assertEquals(4, entity.getFormaPagamento());
        assertEquals("7777", entity.getNumeroCartaoCredito());
        assertEquals(BigDecimal.valueOf(77.77), entity.getValor());
        assertEquals(pagamentoExternoId, entity.getSolicitacaoPagamentoExternoId());
        assertEquals(criado, entity.getDataCriacao());
        assertEquals(alterado, entity.getDataUltimaAlteracao());
    }
}
