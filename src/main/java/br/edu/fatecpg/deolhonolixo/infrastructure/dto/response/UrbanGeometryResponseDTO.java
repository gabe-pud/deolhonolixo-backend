package br.edu.fatecpg.deolhonolixo.infrastructure.dto.response;

import java.util.List;
import java.util.Map;



public record UrbanGeometryResponseDTO (
        String id,
        String type,
        String name,
        String city,
        List<Object> geometry,
        Map<String, Object> metadata
)

{}
