package br.edu.fatecpg.deolhonolixo.infrastructure.dto.response;

import java.time.Instant;

public record TruckHistoryGeolocationResponseDTO(
        Instant timestamp,
        PositionDTO position
) {
    public record PositionDTO(
            Double latitude,
            Double longitude
    ) {}
}
