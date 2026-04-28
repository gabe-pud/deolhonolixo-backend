package br.edu.fatecpg.deolhonolixo.infrastructure.dto.response;

import java.util.List;
import java.util.Map;



public record UrbanGeometryResponseDTO (
        String id,
        String name,
        String city,
        List<List<List<Double>>> coordinates,
        Integer estimatedPopulation,
        String collectionFrequency
) {}
