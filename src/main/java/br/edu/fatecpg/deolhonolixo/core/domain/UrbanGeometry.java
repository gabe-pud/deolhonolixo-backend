package br.edu.fatecpg.deolhonolixo.core.domain;

import java.util.List;
import java.util.Map;

public record UrbanGeometry(
        String id,
        String type,
        String name,
        String city,
        List<Object> geometry,
        Map<String, Object> metadata
) {}