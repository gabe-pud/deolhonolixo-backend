package br.edu.fatecpg.deolhonolixo.infrastructure.dto.response;

import java.util.List;

public record RouteResponseDTO(
        Long id,
        Long routeId,
        String routeName,
        List<String> neighborhoods,
        Object routeGeometry
) {}
