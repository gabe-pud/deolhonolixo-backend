package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.TruckHistory;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.TruckHistoryMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.TruckHistoryDocument;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.TruckHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TruckHistoryPersistenceAdapterTest {

    @Mock
    TruckHistoryRepository repository;

    TruckHistoryMapper mapper = Mappers.getMapper(TruckHistoryMapper.class);

    @InjectMocks
    TruckHistoryPersistenceAdapter adapter;

    @Test
    void saveGeolocation_savesAndReturnsDomain() {
        adapter = new TruckHistoryPersistenceAdapter(repository, mapper);

        TruckHistory input = new TruckHistory(null, Instant.now(), "AAA-1111", 12.0, 34.0, 0);

        TruckHistoryDocument doc = mapper.toDocumentFromDomain(input);
        doc.setId("doc1");

        when(repository.save(any(TruckHistoryDocument.class))).thenReturn(doc);

        TruckHistory out = adapter.saveGeolocation(input);

        assertNotNull(out);
        assertEquals("AAA-1111", out.licensePlate());
    }

    @Test
    void findByLicencePlate_returnsList() {
        adapter = new TruckHistoryPersistenceAdapter(repository, mapper);

        TruckHistoryDocument d = new TruckHistoryDocument();
        d.setId("x");
        d.setLicensePlate("AAA-1111");
        d.setPosition(new org.springframework.data.mongodb.core.geo.GeoJsonPoint(10.0, 20.0));

        when(repository.findAllByLicensePlate("AAA-1111")).thenReturn(List.of(d));

        List<TruckHistory> list = adapter.findByLicencePlate("AAA-1111");

        assertEquals(1, list.size());
        assertEquals("AAA-1111", list.get(0).licensePlate());
    }

    @Test
    void getLastGeolocation_throwsWhenEmpty() {
        adapter = new TruckHistoryPersistenceAdapter(repository, mapper);

        when(repository.findFirstByLicensePlateOrderByTimestampDesc("NOP")).thenReturn(Optional.empty());

        try {
            adapter.getLastGeolocation("NOP");
            fail("expected exception");
        } catch (Exception e) {
            assertTrue(e instanceof RuntimeException || e.getClass().getSimpleName().contains("NotFound"));
        }
    }
}
