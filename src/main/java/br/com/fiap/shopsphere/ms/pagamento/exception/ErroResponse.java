package br.com.fiap.shopsphere.ms.pagamento.exception;

import java.time.LocalDateTime;

public record ErroResponse(int status, String mensagem, LocalDateTime timestamp) {
}
