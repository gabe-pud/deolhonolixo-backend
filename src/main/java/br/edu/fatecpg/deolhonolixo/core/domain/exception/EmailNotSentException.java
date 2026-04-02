package br.edu.fatecpg.deolhonolixo.core.domain.exception;

public class EmailNotSentException extends RuntimeException {
    public EmailNotSentException() { super("Erro no envio do e-mail."); }
    public EmailNotSentException(String message) {
        super(message);
    }
}
