package br.edu.fatecpg.deolhonolixo.core.usecase.city;

import br.edu.fatecpg.deolhonolixo.core.domain.Route;
import br.edu.fatecpg.deolhonolixo.core.gateway.RouteGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.List;


@UseCase
public class RouteFindAllCase {
    private final RouteGateway gateway;

    public RouteFindAllCase(RouteGateway gateway) {
        this.gateway = gateway;
    }

    public List<Route> execute() {
        return gateway.findAll();
    }
}