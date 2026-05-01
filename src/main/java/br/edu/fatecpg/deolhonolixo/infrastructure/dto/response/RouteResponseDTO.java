package br.edu.fatecpg.deolhonolixo.infrastructure.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "RouteResponseDTO", description = "DTO de resposta de pesquisas por Rotas de Coleta.")
public record RouteResponseDTO(
        @Schema(description = "ID da rota", example = "69ed41adcdb8f179531e1a81..")
        String id,
        @Schema(description = "Identificador da rota", example = "PG-LIXO-042")
        String routeId,
        @Schema(description = "Nome da rota", example = "Setor Tupi - Noturno")
        String routeName,
        @Schema(description = "Bairros da rota", example = "[\"Tupi\", \"Aviação\"]")
        List<String> neighborhoods,
        @Schema(description = "Geometria da rota", example = "[[-46.4231, -24.0210], [-46.4250, -24.0225],...]")
        Object routeGeometry
) {}