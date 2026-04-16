package br.edu.fatecpg.deolhonolixo.core.usecase.city;

import br.edu.fatecpg.deolhonolixo.core.domain.city.Route;
import br.edu.fatecpg.deolhonolixo.core.gateway.city.RouteGateway;
import java.util.List;

public class GetRouteUseCase {
    private final RouteGateway gateway;

    public GetRouteUseCase(RouteGateway gateway) {
        this.gateway = gateway;
    }

    public List<Route> execute() {
        return gateway.findAll();
    }

    public Route executeById(Long id) {
        return gateway.findById(id);
    }
}