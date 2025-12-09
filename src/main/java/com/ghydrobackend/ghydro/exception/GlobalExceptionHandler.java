package com.ghydrobackend.ghydro.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice // Isso diz ao Spring: "Fique ouvindo todas as exceções do projeto"
public class GlobalExceptionHandler {

    // Este método captura APENAS a sua exceção personalizada
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<StandardError> regraDeNegocio(RegraDeNegocioException e, HttpServletRequest request) {

        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value()); // Retorna erro 422 (Entidade Improcessável)
        err.setError("Regra de Negócio");
        err.setMessage(e.getMessage()); // A mensagem que você escreveu na Service vem pra cá
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> integridadeDeDados(DataIntegrityViolationException e, HttpServletRequest request) {
        
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        // Status 409 (Conflict) é o mais adequado semanticamente
        err.setStatus(HttpStatus.CONFLICT.value()); 
        err.setError("Conflito de Integridade");
        
        // AQUI ESTÁ A MENSAGEM QUE VAI APARECER NO FLUTTER
        err.setMessage("Não é possível excluir este registro pois ele está vinculado a outros dados do sistema (Ex: Plantios ou Setores).");
        
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }
}

