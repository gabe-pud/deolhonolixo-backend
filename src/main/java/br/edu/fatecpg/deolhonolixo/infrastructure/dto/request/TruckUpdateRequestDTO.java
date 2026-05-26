package br.edu.fatecpg.deolhonolixo.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDateTime;

@Schema(name = "TruckUpdateRequestDTO", description = "DTO para atualização de informações de um caminhão")
public record TruckUpdateRequestDTO(
        @Schema(description = "Status atual do caminhão", example = "EM_ROTA")
        String status,

        @Schema(description = "Data e hora de início da rota", example = "2026-05-25T08:00:00")
        LocalDateTime routeStart,

        @Schema(description = "Data e hora de fim da rota", example = "2026-05-25T18:00:00")
        LocalDateTime routeEnd,

        @Schema(description = "Identificador da rota", example = "PG-LIXO-042")
        String routeId
) {
        @AssertTrue(message = "Preencha pelo menos um campo para atualizar o caminhão.")
        public boolean isAtLeastOneFieldFilled() {
                return status != null || routeStart != null || routeEnd != null || routeId != null;
        }
}
