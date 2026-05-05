package br.edu.fatecpg.deolhonolixo.core.domain.exception;

public class TruckAlreadyRegisteredException extends RuntimeException {
    public TruckAlreadyRegisteredException() {
        super("Placa já cadastrada");
    }
    public TruckAlreadyRegisteredException(String message) {
        super(message);
    }
}
