package br.com.fiap.ms.pagamento.gateway.database.jpa.repository;

import br.com.fiap.ms.pagamento.gateway.database.jpa.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoEntity , UUID> {
    public List<PagamentoEntity> findAllByPedidoId(UUID pedidoId);
}
