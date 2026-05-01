package br.edu.fatecpg.deolhonolixo.core.domain;

import java.util.List;

public record Route(
        String id,
        String routeId,
        String routeName,
        List<String> neighborhoods,
        Object routeGeometry
) {}


