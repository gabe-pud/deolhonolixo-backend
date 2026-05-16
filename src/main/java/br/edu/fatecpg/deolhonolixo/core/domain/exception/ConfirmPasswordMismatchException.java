package br.edu.fatecpg.deolhonolixo.core.domain.exception;

public class ConfirmPasswordMismatchException extends RuntimeException {
    public ConfirmPasswordMismatchException() {
        super("A confirmação de senha não confere com a senha digitada");
    }
    public ConfirmPasswordMismatchException(String message) {
        super(message);
    }
}
