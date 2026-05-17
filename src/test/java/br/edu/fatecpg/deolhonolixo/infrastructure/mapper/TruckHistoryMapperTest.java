package br.edu.fatecpg.deolhonolixo.infrastructure.mapper;

import br.edu.fatecpg.deolhonolixo.core.domain.TruckHistory;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckHistoryFindResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckHistoryGeolocationResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.TruckHistoryDocument;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.TruckTelemetryData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TruckHistoryMapperTest {
    private final TruckHistoryMapper mapper = Mappers.getMapper(TruckHistoryMapper.class);

    @Test
    void shouldMapDomainToDocument() {
        Instant timestamp = Instant.parse("2026-05-17T15:30:00Z");
        TruckHistory history = new TruckHistory("1", timestamp, "ABC-1234", -46.40, -24.00, 42);

        TruckHistoryDocument result = mapper.toDocumentFromDomain(history);

        assertAll(
                () -> assertEquals(history.id(), result.getId()),
                () -> assertEquals(history.timestamp(), result.getTimestamp()),
                () -> assertEquals(history.licensePlate(), result.getLicensePlate()),
                () -> assertEquals(history.longitude(), result.getPosition().getX()),
                () -> assertEquals(history.latitude(), result.getPosition().getY()),
                () -> assertEquals(history.speedKmh(), result.getTelemetry().getSpeedKmh())
        );
    }

    @Test
    void shouldMapDocumentToDomain() {
        Instant timestamp = Instant.parse("2026-05-17T15:30:00Z");
        TruckHistoryDocument document = new TruckHistoryDocument(
                "1",
                timestamp,
                "ABC-1234",
                new GeoJsonPoint(-46.40, -24.00),
                new TruckTelemetryData(42)
        );

        TruckHistory result = mapper.toDomainFromDocument(document);

        assertAll(
                () -> assertEquals(document.getId(), result.id()),
                () -> assertEquals(document.getTimestamp(), result.timestamp()),
                () -> assertEquals(document.getLicensePlate(), result.licensePlate()),
                () -> assertEquals(document.getPosition().getX(), result.longitude()),
                () -> assertEquals(document.getPosition().getY(), result.latitude()),
                () -> assertEquals(document.getTelemetry().getSpeedKmh(), result.speedKmh())
        );
    }

    @Test
    void shouldMapHistoryToFindResponseDto() {
        Instant timestamp = Instant.parse("2026-05-17T15:30:00Z");
        TruckHistory history = new TruckHistory("1", timestamp, "ABC-1234", -46.40, -24.00, 42);

        TruckHistoryFindResponseDTO result = mapper.toHistoryFindResponseDTO(history);

        assertAll(
                () -> assertEquals(history.timestamp(), result.timestamp()),
                () -> assertEquals(history.licensePlate(), result.licensePlate()),
                () -> assertEquals(history.latitude(), result.position().latitude()),
                () -> assertEquals(history.longitude(), result.position().longitude()),
                () -> assertEquals(history.speedKmh(), result.telemetry().speedKmh())
        );
    }

    @Test
    void shouldMapHistoryToGeolocationResponseDto() {
        Instant timestamp = Instant.parse("2026-05-17T15:30:00Z");
        TruckHistory history = new TruckHistory("1", timestamp, "ABC-1234", -46.40, -24.00, 42);

        TruckHistoryGeolocationResponseDTO result = mapper.toHistoryGeolocationResponseDTO(history);

        assertAll(
                () -> assertEquals(history.timestamp(), result.timestamp()),
                () -> assertEquals(history.latitude(), result.position().latitude()),
                () -> assertEquals(history.longitude(), result.position().longitude())
        );
    }

    @Test
    void shouldReturnNullGeoJsonPointWhenHistoryIsIncomplete() {
        assertNull(mapper.mapToGeoJsonPoint(new TruckHistory("1", Instant.now(), "ABC-1234", null, -24.00, 42)));
        assertNull(mapper.mapToGeoJsonPoint(new TruckHistory("1", Instant.now(), "ABC-1234", -46.40, null, 42)));
        assertNull(mapper.mapToGeoJsonPoint(null));
    }
}