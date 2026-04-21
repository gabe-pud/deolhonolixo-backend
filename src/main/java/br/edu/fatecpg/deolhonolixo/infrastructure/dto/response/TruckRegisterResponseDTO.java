package br.edu.fatecpg.deolhonolixo.infrastructure.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TruckRegisterResponseDTO(
        @Schema(description = "Placa do Caminhão", example = "ABC1D23")
        String licensePlate
) { }
