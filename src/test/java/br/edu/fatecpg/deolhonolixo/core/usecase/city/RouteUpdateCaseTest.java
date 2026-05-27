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
class RouteUpdateCaseTest {

    @Mock
    RouteGateway gateway;

    @InjectMocks
    RouteUpdateCase routeUpdateCase;

    @Test
    void execute_updatesOnlyAllowedFieldsAndKeepsRouteId() {
        Route current = new Route("10", "R1", "Old name", List.of("Centro"), List.of(List.of(-46.4, -24.0)));
        Route updateRequest = new Route(null, "R1", "New name", List.of("Tupi"), List.of(List.of(-46.5, -24.1)));
        Route saved = new Route("10", "R1", "New name", List.of("Tupi"), List.of(List.of(-46.5, -24.1)));

        when(gateway.findByRouteId("R1")).thenReturn(current);
        when(gateway.save(new Route("10", "R1", "New name", List.of("Tupi"), List.of(List.of(-46.5, -24.1))))).thenReturn(saved);

        Route result = routeUpdateCase.execute(updateRequest);

        assertEquals("10", result.id());
        assertEquals("R1", result.routeId());
        assertEquals("New name", result.routeName());
    }
}