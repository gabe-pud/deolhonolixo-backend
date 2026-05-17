package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.UrbanGeometry;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.UrbanGeometryMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.UrbanGeometryDocument;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.UrbanGeometryMongoRepository;
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
class UrbanGeometryPersistenceAdapterTest {

    @Mock
    UrbanGeometryMongoRepository repository;

    UrbanGeometryMapper mapper = Mappers.getMapper(UrbanGeometryMapper.class);

    @InjectMocks
    UrbanGeometryPersistenceAdapter adapter;

    @Test
    void findAll_mapsDocumentsToDomain() {
        adapter = new UrbanGeometryPersistenceAdapter(repository, mapper);

        UrbanGeometryDocument d = new UrbanGeometryDocument();
        d.setId("g1");
        d.setName("Geom 1");

        when(repository.findAll()).thenReturn(List.of(d));

        List<UrbanGeometry> list = adapter.findAll();

        assertEquals(1, list.size());
        assertEquals("Geom 1", list.get(0).name());
    }

    @Test
    void findById_returnsDomain() {
        adapter = new UrbanGeometryPersistenceAdapter(repository, mapper);

        UrbanGeometryDocument d = new UrbanGeometryDocument();
        d.setId("g2");
        d.setName("Geom 2");

        when(repository.findById("g2")).thenReturn(Optional.of(d));

        var res = adapter.findById("g2");

        assertEquals("Geom 2", res.name());
    }

    @Test
    void findById_throwsWhenNotFound() {
        adapter = new UrbanGeometryPersistenceAdapter(repository, mapper);

        when(repository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adapter.findById("missing"));
    }

    @Test
    void findByName_mapsDocumentToDomain() {
        adapter = new UrbanGeometryPersistenceAdapter(repository, mapper);

        UrbanGeometryDocument d = new UrbanGeometryDocument();
        d.setId("g3");
        d.setName("Geom X");

        when(repository.findOneByName("Geom X")).thenReturn(d);

        var res = adapter.findByName("Geom X");

        assertEquals("Geom X", res.name());
    }
}
