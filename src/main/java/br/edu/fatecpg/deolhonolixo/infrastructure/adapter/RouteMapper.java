package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.city.Route;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.RouteDocument;

import br.edu.fatecpg.deolhonolixo.core.domain.city.Route;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.RouteResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.RouteDocument;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {

    public Route toDomain(RouteDocument doc) {
        return new Route(
                doc.getId(), doc.getRouteId(), doc.getRouteName(),
                doc.getNeighborhoods(), doc.getRouteGeometry()
        );
    }

    public RouteResponseDTO toResponseDTO(Route r) {
        return new RouteResponseDTO(
                r.id(), r.routeId(), r.routeName(),
                r.neighborhoods(), r.routeGeometry()
        );
    }
}
