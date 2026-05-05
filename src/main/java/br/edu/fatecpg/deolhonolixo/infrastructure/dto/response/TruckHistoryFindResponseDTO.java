package br.edu.fatecpg.deolhonolixo.infrastructure.dto.response;

import java.time.Instant;

public record TruckHistoryFindResponseDTO(
        Instant timestamp,
        String licensePlate,
        PositionDTO position,
        TelemetryDTO telemetry
) {
    public record PositionDTO(
            Double latitude,
            Double longitude
    ) {}

    public record TelemetryDTO(
            Integer speedKmh
    ){}
}
