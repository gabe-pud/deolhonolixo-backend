package br.edu.fatecpg.deolhonolixo.core.domain.exception;

public class TruckGeolocationNotFoundException extends RuntimeException {
    public TruckGeolocationNotFoundException() {
        super("Geolocalização não encontrada para este caminhão");
    }
    public TruckGeolocationNotFoundException(String message) {
        super(message);
    }
}
