package br.edu.fatecpg.deolhonolixo.core.domain;

import java.util.List;

public record UrbanGeometry(
        String id,
        String name,
        String city,
        List<List<Double[]>> geometry,
        String collectionPeriod,
        String collectionTime,
        List<String> collectionDays
) {}