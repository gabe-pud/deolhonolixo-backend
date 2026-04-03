package br.edu.fatecpg.deolhonolixo.core.domain.exception;

public class ConfirmPasswordMismatchExeption extends RuntimeException {
    public ConfirmPasswordMismatchExeption() {
        super("A confirmação de senha não confere com a senha digitada");
    }
    public ConfirmPasswordMismatchExeption(String message) {
        super(message);
    }
}
