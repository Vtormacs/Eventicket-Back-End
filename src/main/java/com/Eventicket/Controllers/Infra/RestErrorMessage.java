package com.Eventicket.Controllers.Infra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RestErrorMessage {

    private HttpStatus status;        // Código HTTP
    private String mensagem;          // Mensagem amigável para o usuário
    private String errorCode;         // Código de erro específico
    private LocalDateTime timestamp;  // Data e hora do erro
    private String detalhes;          // Detalhes adicionais sobre o erro

    // Construtor padrão, inicializando o timestamp automaticamente
    public RestErrorMessage(HttpStatus status, String mensagem, String errorCode) {
        this.status = status;
        this.mensagem = mensagem;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }
}
