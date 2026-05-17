package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.Route;
import br.edu.fatecpg.deolhonolixo.core.usecase.city.RouteFindAllCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.city.RouteFindByRouteIdCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.RouteResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.RouteMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteControllerTest {

    @Mock
    RouteFindAllCase findAllCase;

    @Mock
    RouteFindByRouteIdCase findByRouteIdCase;

    @Mock
    RouteMapper mapper;

    @InjectMocks
    RouteController controller;

    @Test
    void listarTodas_returns200() {
        Route domain = new Route("r1","rid","nome", List.of(), null);
        RouteResponseDTO dto = new RouteResponseDTO("r1","rid","nome", List.of(), null);

        when(findAllCase.execute()).thenReturn(List.of(domain));
        when(mapper.toResponseDTO(domain)).thenReturn(dto);

        var resp = controller.listarTodas();

        assertEquals(200, resp.getStatusCode().value());
        assertEquals("nome", resp.getBody().get(0).routeName());
    }

    @Test
    void buscarPorId_returns200() {
        Route domain = new Route("r2","rid2","rota", List.of(), null);
        RouteResponseDTO dto = new RouteResponseDTO("r2","rid2","rota", List.of(), null);

        when(findByRouteIdCase.execute("r2")).thenReturn(domain);
        when(mapper.toResponseDTO(domain)).thenReturn(dto);

        var resp = controller.buscarPorId("r2");

        assertEquals(200, resp.getStatusCode().value());
        assertEquals("rota", resp.getBody().routeName());
    }
}
