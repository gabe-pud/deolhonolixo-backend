package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TruckHistoryRepositoryTest {

    @Mock
    TruckHistoryRepository repository;

    @Test
    void saveAndQuery_contract() {
        TruckHistoryDocument d = new TruckHistoryDocument();
        d.setId("h1");
        d.setLicensePlate("T-111");
        d.setTimestamp(Instant.now());
        d.setPosition(new GeoJsonPoint(12.3, 45.6));

        when(repository.save(any(TruckHistoryDocument.class))).thenReturn(d);
        when(repository.findAllByLicensePlate("T-111")).thenReturn(List.of(d));

        var saved = repository.save(new TruckHistoryDocument());
        assertEquals("h1", saved.getId());

        var all = repository.findAllByLicensePlate("T-111");
        assertFalse(all.isEmpty());
    }
}
