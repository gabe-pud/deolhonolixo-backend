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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteFindByRouteIdCaseTest {
    @Mock
    private RouteGateway gateway;
    @InjectMocks
    private RouteFindByRouteIdCase useCase;

    @Test
    void shouldReturnRouteByRouteId() {
        String routeId = "R1";
        Route route = new Route("1", routeId, "Route 1", List.of("Centro"), null);

        when(gateway.findByRouteId(routeId)).thenReturn(route);

        Route result = useCase.execute(routeId);

        assertEquals(route, result);
        verify(gateway).findByRouteId(routeId);
    }
}