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
class RouteFindAllCaseTest {
    @Mock
    private RouteGateway gateway;
    @InjectMocks
    private RouteFindAllCase useCase;

    @Test
    void shouldReturnAllRoutes() {
        List<Route> routes = List.of(
                new Route("1", "R1", "Route 1", List.of("Centro"), null),
                new Route("2", "R2", "Route 2", List.of("Bairro A"), null)
        );
        when(gateway.findAll()).thenReturn(routes);

        List<Route> result = useCase.execute();

        assertEquals(routes, result);
        verify(gateway).findAll();
    }
}