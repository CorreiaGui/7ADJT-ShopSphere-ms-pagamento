package br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa;

import br.com.fiap.shopsphere.ms.pagamento.domain.Pagamento;
import br.com.fiap.shopsphere.ms.pagamento.gateway.PagamentoGateway;
import br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import br.com.fiap.shopsphere.ms.pagamento.gateway.database.jpa.repository.PagamentoRepository;
import br.com.fiap.shopsphere.ms.pagamento.utils.PagamentoUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.PageRequest.of;

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
        return pagamentoRepository.findById(id)
                .map(PagamentoUtils::convertToPagamento);
    }


    @Override
    public List<Pagamento> buscarTodosPagamentos(int page, int size) {
        Page<PagamentoEntity> pagamentoEntity = pagamentoRepository.findAll(of(page, size));
        return pagamentoEntity.map(PagamentoUtils::convertToPagamento).getContent();
    }

    @Override
    public PagamentoEntity criarPagamento(PagamentoEntity entity) {
        return pagamentoRepository.save(entity);
    }

    @Override
    public void excluirPagamento(UUID id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new RuntimeException("Pagamento não encontrado - ID: " + id);
        }
        pagamentoRepository.deleteById(id);
    }

    @Override
    public PagamentoEntity alterarPagamento(PagamentoEntity entity) {
        return pagamentoRepository.save(entity);
    }
}
