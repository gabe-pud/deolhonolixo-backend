package br.edu.fatecpg.deolhonolixo.core.usecase.city;

import br.edu.fatecpg.deolhonolixo.core.domain.Route;
import br.edu.fatecpg.deolhonolixo.core.gateway.RouteGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

@UseCase
public class RouteSaveCase {
    private final RouteGateway gateway;

    public RouteSaveCase(RouteGateway gateway) {
        this.gateway = gateway;
    }

    public Route execute(Route route) {
        return gateway.save(route);
    }
}
