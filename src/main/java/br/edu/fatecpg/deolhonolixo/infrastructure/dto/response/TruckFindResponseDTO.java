package br.edu.fatecpg.deolhonolixo.infrastructure.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

import java.util.Date;

public record TruckFindResponseDTO(
        @Schema(description = "ID do caminhão", example = "123")
        Long id,
        @Schema(description = "Placa do Caminhão", example = "ABC1D23")
        String licensePlate,
        @Schema(description = "Placa do Caminhão", example = "ABC1D23")
        String status,
        Date routeStart,
        Date routeEnd,
        @Schema(description = "ID da rota para qual este caminhão foi designado", example = "PG-LIXO-042")
        @Nullable
        String routeId
) { }
