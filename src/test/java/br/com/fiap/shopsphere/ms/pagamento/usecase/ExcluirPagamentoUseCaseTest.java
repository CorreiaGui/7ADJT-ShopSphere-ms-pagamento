package br.com.fiap.shopsphere.ms.pagamento.usecase;

import br.com.fiap.shopsphere.ms.pagamento.gateway.PagamentoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class ExcluirPagamentoUseCaseTest {

    private PagamentoGateway gateway;
    private ExcluirPagamentoUseCase useCase;

    @BeforeEach
    void setup() {
        gateway = mock(PagamentoGateway.class);
        useCase = new ExcluirPagamentoUseCase();
        // simula injeção manual por ser @Autowired
        var field = ExcluirPagamentoUseCase.class.getDeclaredFields()[0];
        field.setAccessible(true);
        try {
            field.set(useCase, gateway);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deveExcluirPagamentoComSucesso() {
        UUID id = UUID.randomUUID();

        useCase.excluirPagamento(id);

        verify(gateway).excluirPagamento(id);
    }
}
