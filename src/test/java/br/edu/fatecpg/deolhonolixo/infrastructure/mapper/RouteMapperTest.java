package br.edu.fatecpg.deolhonolixo.infrastructure.mapper;

import br.edu.fatecpg.deolhonolixo.core.domain.Route;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.RouteResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.RouteDocument;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteMapperTest {
    private final RouteMapper mapper = Mappers.getMapper(RouteMapper.class);

    @Test
    void shouldMapDocumentToDomain() {
        RouteDocument document = new RouteDocument(
                "1",
                "R1",
                "Route 1",
                List.of("Centro", "Bairro A"),
                List.of(List.of(-46.4, -24.0))
        );

        Route result = mapper.toDomain(document);

        assertAll(
                () -> assertEquals(document.getId(), result.id()),
                () -> assertEquals(document.getRouteId(), result.routeId()),
                () -> assertEquals(document.getRouteName(), result.routeName()),
                () -> assertEquals(document.getNeighborhoods(), result.neighborhoods()),
                () -> assertEquals(document.getRouteGeometry(), result.routeGeometry())
        );
    }

    @Test
    void shouldMapDomainToResponseDto() {
        Route route = new Route("1", "R1", "Route 1", List.of("Centro", "Bairro A"), List.of(List.of(-46.4, -24.0)));

        RouteResponseDTO result = mapper.toResponseDTO(route);

        assertAll(
                () -> assertEquals(route.id(), result.id()),
                () -> assertEquals(route.routeId(), result.routeId()),
                () -> assertEquals(route.routeName(), result.routeName()),
                () -> assertEquals(route.neighborhoods(), result.neighborhoods()),
                () -> assertEquals(route.routeGeometry(), result.routeGeometry())
        );
    }
}