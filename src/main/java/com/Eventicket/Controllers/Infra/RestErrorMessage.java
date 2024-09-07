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

    private HttpStatus status;
    private String mensagem;
    private String errorCode;
    private LocalDateTime timestamp;
    private String detalhes;

    public RestErrorMessage(HttpStatus status, String mensagem, String errorCode) {
        this.status = status;
        this.mensagem = mensagem;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }
}
