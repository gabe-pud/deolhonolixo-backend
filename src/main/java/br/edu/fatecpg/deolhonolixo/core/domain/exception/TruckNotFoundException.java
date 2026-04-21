package br.edu.fatecpg.deolhonolixo.core.domain.exception;

public class TruckNotFoundException extends RuntimeException {
    public TruckNotFoundException() {
        super("Caminhão não encontrado.");
    }
    public TruckNotFoundException(String message) {
        super(message);
    }
}
