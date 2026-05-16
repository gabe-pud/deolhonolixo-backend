package br.edu.fatecpg.deolhonolixo.infrastructure.exception;

import br.edu.fatecpg.deolhonolixo.core.domain.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConfirmPasswordMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handlePasswordMismatch(ConfirmPasswordMismatchException e) {
        int status = 422;
        ErrorResponseDTO error = new ErrorResponseDTO(Instant.now(), status, "A confirmação de senha não confere com a senha digitada");

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyRegistered(UserAlreadyRegisteredException e) {
        int status = 409;
        ErrorResponseDTO error = new ErrorResponseDTO(Instant.now(), status, "Nome de usuário ou e-mail já cadastrado");

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(LoginValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleLoginValidation(LoginValidationException e) {
        int status = 400;
        ErrorResponseDTO error = new ErrorResponseDTO(Instant.now(), status, "Credenciais inválidas");

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(TruckNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleTruckNotFound(TruckNotFoundException e) {
        int status = 400;
        ErrorResponseDTO error = new ErrorResponseDTO(Instant.now(), status, "Caminhão não encontrado");

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(TruckAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponseDTO> handleTruckAlreadyRegistered(TruckAlreadyRegisteredException e) {
        int status = 409;
        ErrorResponseDTO error = new ErrorResponseDTO(Instant.now(), status, "Caminhão já cadastrado");

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(TruckGeolocationNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleTruckGeolocationNotFound(TruckGeolocationNotFoundException e) {
        int status = 400;
        ErrorResponseDTO error = new ErrorResponseDTO(Instant.now(), status, "Caminhão não encontrado");

        return ResponseEntity.status(status).body(error);
    }
}
