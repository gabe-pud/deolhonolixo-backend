package br.edu.fatecpg.deolhonolixo.core.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuário não encontrado.");
    }
    public UserNotFoundException(String message) {
        super(message);
    }
}
