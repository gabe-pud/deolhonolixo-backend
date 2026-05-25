package br.edu.fatecpg.deolhonolixo.core.usecase.city;

import br.edu.fatecpg.deolhonolixo.core.domain.Route;
import br.edu.fatecpg.deolhonolixo.core.gateway.RouteGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteSaveCaseTest {

    @Mock
    RouteGateway gateway;

    @InjectMocks
    RouteSaveCase routeSaveCase;

    @Test
    void execute_savesRouteThroughGateway() {
        Route route = new Route(null, "R1", "Route 1", List.of("Centro"), List.of(List.of(-46.4, -24.0)));

        when(gateway.save(route)).thenReturn(new Route("1", "R1", "Route 1", List.of("Centro"), route.routeGeometry()));

        Route result = routeSaveCase.execute(route);

        assertEquals("1", result.id());
        assertEquals("R1", result.routeId());
    }
}