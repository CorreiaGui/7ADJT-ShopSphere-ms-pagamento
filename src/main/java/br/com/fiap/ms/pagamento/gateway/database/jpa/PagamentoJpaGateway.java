package br.com.fiap.ms.pagamento.gateway.database.jpa;

import br.com.fiap.ms.pagamento.domain.Pagamento;
import br.com.fiap.ms.pagamento.gateway.PagamentoGateway;
import br.com.fiap.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import br.com.fiap.ms.pagamento.gateway.database.jpa.repository.PagamentoRepository;
import br.com.fiap.ms.pagamento.utils.PagamentoUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class PagamentoJpaGateway implements PagamentoGateway {
    private final PagamentoRepository pagamentoRepository;

    @Override
    public List<Pagamento> buscarPagamentosPorPedido(UUID pedidoId) {
        List<PagamentoEntity> pagamentoEntity = pagamentoRepository.findAllByPedidoId(pedidoId);
        return pagamentoEntity.stream()
                .map(PagamentoUtils::convertToPagamento)
                .collect(toList());
    }

    @Override
    public Optional<Pagamento> buscarPorId(UUID id) {
        var pagamentoEntity = pagamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não encontrado - id: " + id));
        return ofNullable(PagamentoUtils.convertToPagamento(pagamentoEntity));
    }

    @Override
    public List<Pagamento> buscarTodosPagamentos() {
        return pagamentoRepository.findAll().stream()
                .map(PagamentoUtils::convertToPagamento)
                .collect(toList());
    }
}
