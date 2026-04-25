package br.edu.fatecpg.deolhonolixo.infrastructure.dto.response;

import java.util.List;

public record RouteResponseDTO(
        String id,
        String routeId,
        String routeName,
        List<String> neighborhoods,
        Object routeGeometry
) {}
