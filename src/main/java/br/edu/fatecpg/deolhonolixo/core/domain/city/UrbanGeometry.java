package br.edu.fatecpg.deolhonolixo.core.domain.city;

import java.util.List;
import java.util.Map;

public record UrbanGeometry(
        Long id,
        String type,
        String name,
        String city,
        List<Object> geometry,
        Map<String, Object> metadata
) {}