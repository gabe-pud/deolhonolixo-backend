package br.edu.fatecpg.deolhonolixo.infrastructure.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;


@Schema(name = "UrbanGeometryResponseDTO", description = "DTO de resposta de pesquisas por Geometria Urbana.")
public record UrbanGeometryResponseDTO (
        @Schema(description = "ID urbanGeometry", example = "69ed41adcdb8f179531e1a81..")
        String id,
        @Schema(description = "Nome Do Bairros ou Ruas", example = "Tupi/Frei Damião")
        String name,
        @Schema(description = "Nome da Cidade", example = "Praia Grande")
        String city,
        @Schema(description = "Formato do Bairro ou Rua", example = "[[-46.4231, -24.0210], [-46.4240, -24.0220],...]")
        List<List<List<Double>>> coordinates,
        @Schema(description = "Estimativa Populacional do Bairro", example = "2000")
        Integer estimatedPopulation,
        @Schema(description = "Frequencia de Coleta", example = "Diaria")
        String collectionFrequency
) {}
