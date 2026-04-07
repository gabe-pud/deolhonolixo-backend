package br.edu.fatecpg.deolhonolixo.core.domain.exception;

public class LoginValidationException extends RuntimeException {
    public LoginValidationException() {
        super("Credenciais inválidas");
    }
    public LoginValidationException(Throwable cause) {
        super("Credenciais inválidas", cause);
    }
    public LoginValidationException(String message) {
        super(message);
    }
}
