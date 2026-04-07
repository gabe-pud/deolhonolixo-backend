package br.edu.fatecpg.deolhonolixo.core.domain.exception;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException() {
        super("Nome de usuário ou email já cadastrado");
    }
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
