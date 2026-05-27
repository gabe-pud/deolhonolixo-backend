package br.edu.fatecpg.deolhonolixo.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(name = "RouteSaveRequestDTO", description = "DTO para dados básicos de uma rota antes do upload do JSON.")
public record RouteSaveRequestDTO(
        @Schema(description = "Identificador da rota", example = "PG-LIXO-042")
        @NotBlank(message = "O routeId é obrigatório.")
        String routeId,

        @Schema(description = "Nome da rota", example = "Setor Tupi - Noturno")
        @NotBlank(message = "O nome da rota é obrigatório.")
        String routeName,

        @Schema(description = "Bairros atendidos pela rota", example = "[\"Tupi\", \"Aviação\"]")
        @NotEmpty(message = "A rota deve ter ao menos um bairro.")
        List<String> neighborhoods,

        @Schema(
                description = "Geometria da rota em JSON livre, normalmente o resultado do algoritmo da rota.",
                example = "{\"type\":\"MultiLineString\", \"coordinates\": [[[-46.412190315079854, -24.00532760371618 ], [-46.41233425116133, -24.004472937186726], [-46.411387058846564, -24.004926780663034]]]}"
        )
        @NotNull(message = "A geometria da rota é obrigatória.")
        @Valid
        RouteGeometryDTO routeGeometry
) {
        public record RouteGeometryDTO(
                @NotBlank(message = "O tipo da geometria é obrigatório.")
                String type,

                @NotEmpty(message = "A geometria precisa ter coordenadas.")
                List<List<List<Double>>> coordinates
        ){}
}