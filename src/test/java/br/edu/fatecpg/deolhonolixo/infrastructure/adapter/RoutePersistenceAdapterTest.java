package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.Route;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.RouteMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.RouteDocument;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.RouteMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoutePersistenceAdapterTest {

    @Mock
    RouteMongoRepository repository;

    RouteMapper mapper = Mappers.getMapper(RouteMapper.class);

    @InjectMocks
    RoutePersistenceAdapter adapter;

    @Test
    void findAll_mapsDocumentsToDomain() {
        adapter = new RoutePersistenceAdapter(repository, mapper);

        RouteDocument d = new RouteDocument();
        d.setId("1");
        d.setRouteId("R1");
        d.setRouteName("Route 1");

        when(repository.findAll()).thenReturn(List.of(d));

        List<Route> routes = adapter.findAll();

        assertEquals(1, routes.size());
        assertEquals("R1", routes.get(0).routeId());
    }

    @Test
    void findById_returnsDomain() {
        adapter = new RoutePersistenceAdapter(repository, mapper);

        RouteDocument d = new RouteDocument();
        d.setId("r2");
        d.setRouteId("R2");

        when(repository.findById("r2")).thenReturn(Optional.of(d));

        var res = adapter.findById("r2");

        assertEquals("R2", res.routeId());
    }

    @Test
    void findById_throwsWhenNotFound() {
        adapter = new RoutePersistenceAdapter(repository, mapper);

        when(repository.findById("no")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adapter.findById("no"));
    }

    @Test
    void findByRouteId_mapsDocumentToDomain() {
        adapter = new RoutePersistenceAdapter(repository, mapper);

        RouteDocument d = new RouteDocument();
        d.setId("r3");
        d.setRouteId("R3");
        d.setRouteName("Name 3");

        when(repository.findOneByRouteId("R3")).thenReturn(d);

        var res = adapter.findByRouteId("R3");

        assertEquals("R3", res.routeId());
    }
}
