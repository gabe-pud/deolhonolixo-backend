package br.edu.fatecpg.deolhonolixo.infrastructure.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

public record TruckSearchResponseDTO(
        @Schema(description = "Placa do Caminhão", example = "ABC1D23")
        String licensePlate,
        @Schema(description = "ID da rota para qual este caminão foi designado", example = "PG-LIXO-042")
        @Nullable
        String routeId
) { }
