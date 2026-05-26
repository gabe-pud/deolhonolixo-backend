package br.edu.fatecpg.deolhonolixo.infrastructure.mapper;

import br.edu.fatecpg.deolhonolixo.core.domain.UrbanGeometry;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.UrbanGeometryResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.UrbanGeometryDocument;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.UrbanGeometryMetadata;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UrbanGeometryMapperTest {
    private final UrbanGeometryMapper mapper = Mappers.getMapper(UrbanGeometryMapper.class);

    @Test
    void shouldMapDocumentToDomain() {
        GeoJsonPolygon polygon = new GeoJsonPolygon(List.of(
                new Point(-46.40, -24.00),
                new Point(-46.41, -24.01),
                new Point(-46.42, -24.02),
                new Point(-46.40, -24.00)
        ));
        UrbanGeometryMetadata metadata = new UrbanGeometryMetadata("Dia", "18:00", List.of("SEG", "QUA"));
        UrbanGeometryDocument document = new UrbanGeometryDocument("1", "Feature", "Centro", "Praia Grande", polygon, metadata);

        UrbanGeometry result = mapper.toDomain(document);

        assertAll(
                () -> assertEquals(document.getId(), result.id()),
                () -> assertEquals(document.getName(), result.name()),
                () -> assertEquals(document.getCity(), result.city()),
                () -> assertEquals(metadata.getCollectionPeriod(), result.collectionPeriod()),
                () -> assertEquals(metadata.getCollectionTime(), result.collectionTime()),
                () -> assertEquals(metadata.getCollectionDays(), result.collectionDays())
        );
        assertEquals(1, result.geometry().size());
        assertEquals(4, result.geometry().get(0).size());
        assertArrayEquals(new Double[]{-46.40, -24.00}, result.geometry().get(0).get(0));
        assertArrayEquals(new Double[]{-46.41, -24.01}, result.geometry().get(0).get(1));
    }

    @Test
    void shouldMapDomainToResponseDto() {
        List<List<Double[]>> geometry = List.of(
                List.of(
                        new Double[]{-46.40, -24.00},
                        new Double[]{-46.41, -24.01}
                )
        );
        UrbanGeometry domain = new UrbanGeometry("1", "Centro", "Praia Grande", geometry, "Dia", "18:00", List.of("SEG", "QUA"));

        UrbanGeometryResponseDTO result = mapper.toResponseDTO(domain);

        assertAll(
                () -> assertEquals(domain.id(), result.id()),
                () -> assertEquals(domain.name(), result.name()),
                () -> assertEquals(domain.city(), result.city()),
                () -> assertEquals(domain.collectionPeriod(), result.collectionPeriod()),
                () -> assertEquals(domain.collectionTime(), result.collectionTime()),
                () -> assertEquals(domain.collectionDays(), result.collectionDays())
        );
        assertEquals(List.of(List.of(List.of(-46.40, -24.00), List.of(-46.41, -24.01))), result.geometry());
    }

    @Test
    void shouldMapPolygonToCoords() {
        GeoJsonPolygon polygon = new GeoJsonPolygon(List.of(
                new Point(-46.40, -24.00),
                new Point(-46.41, -24.01),
                new Point(-46.42, -24.02),
                new Point(-46.40, -24.00)
        ));

        List<List<Double[]>> result = mapper.mapPolygonToCoords(polygon);

        assertEquals(1, result.size());
        assertEquals(4, result.get(0).size());
        assertArrayEquals(new Double[]{-46.40, -24.00}, result.get(0).get(0));
    }

    @Test
    void shouldMapCoordinatesToNestedLists() {
        List<List<Double[]>> geometry = List.of(
                List.of(
                        new Double[]{-46.40, -24.00},
                        new Double[]{-46.41, -24.01}
                )
        );

        List<List<List<Double>>> result = mapper.mapCoordinates(geometry);

        assertEquals(List.of(List.of(List.of(-46.40, -24.00), List.of(-46.41, -24.01))), result);
    }

    @Test
    void shouldReturnNullForNullPolygon() {
        assertNull(mapper.mapPolygonToCoords(null));
    }

    @Test
    void shouldReturnNullForNullCoordinates() {
        assertNull(mapper.mapCoordinates(null));
    }
}