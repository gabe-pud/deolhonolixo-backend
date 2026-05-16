package br.edu.fatecpg.deolhonolixo.infrastructure.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


@Schema(name = "TruckRegisterRequestDTO", description = "DTO para registro de um novo caminhão")
public record TruckRegisterRequestDTO(
        @Schema(description = "Placa do Caminhão", example = "ABC1D23")
        @NotBlank(message = "A placa é obrigatória.")
        String licensePlate
) { }
