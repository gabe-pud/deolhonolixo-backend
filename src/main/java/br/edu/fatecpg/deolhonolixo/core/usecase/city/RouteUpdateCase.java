package br.edu.fatecpg.deolhonolixo.core.usecase.city;

import br.edu.fatecpg.deolhonolixo.core.domain.Route;
import br.edu.fatecpg.deolhonolixo.core.gateway.RouteGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.List;

@UseCase
public class RouteUpdateCase {
    private final RouteGateway gateway;

    public RouteUpdateCase(RouteGateway gateway) {
        this.gateway = gateway;
    }

    public Route execute(Route route) {
        if (route.neighborhoods() == null || route.neighborhoods().isEmpty()) {
            throw new IllegalArgumentException("A rota deve ter ao menos um bairro.");
        }

        Route currentRoute = gateway.findByRouteId(route.routeId());
        Route updatedRoute = new Route(
                currentRoute.id(),
                currentRoute.routeId(),
                route.routeName(),
                route.neighborhoods(),
                route.routeGeometry()
        );

        return gateway.save(updatedRoute);
    }
}