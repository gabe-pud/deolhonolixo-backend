package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouteMongoRepositoryTest {

    @Mock
    RouteMongoRepository repository;

    @Test
    void saveAndFind_contract() {
        RouteDocument d = new RouteDocument();
        d.setId("doc1");
        d.setRouteId("R-IT-1");
        d.setRouteName("Route IT 1");

        when(repository.save(any(RouteDocument.class))).thenReturn(d);
        when(repository.findAll()).thenReturn(List.of(d));
        when(repository.findOneByRouteId("R-IT-1")).thenReturn(d);

        var saved = repository.save(new RouteDocument());
        assertEquals("doc1", saved.getId());

        List<RouteDocument> all = repository.findAll();
        assertTrue(all.stream().anyMatch(r -> "R-IT-1".equals(r.getRouteId())));

        var byRouteId = repository.findOneByRouteId("R-IT-1");
        assertNotNull(byRouteId);
    }
}
