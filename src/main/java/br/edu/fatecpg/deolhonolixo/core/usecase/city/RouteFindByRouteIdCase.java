package br.edu.fatecpg.deolhonolixo.core.usecase.city;

import br.edu.fatecpg.deolhonolixo.core.domain.Route;
import br.edu.fatecpg.deolhonolixo.core.gateway.RouteGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

@UseCase
public class RouteFindByRouteIdCase {
    private final RouteGateway gateway;

    public RouteFindByRouteIdCase(RouteGateway gateway) {
        this.gateway = gateway;
    }

    public Route execute(String id) {
        return gateway.findByRouteId(id);
    }
}
