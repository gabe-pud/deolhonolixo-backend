package br.edu.fatecpg.deolhonolixo.infrastructure.mapper;

import br.edu.fatecpg.deolhonolixo.core.domain.UrbanGeometry;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.UrbanGeometryResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.UrbanGeometryDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UrbanGeometryMapper {
    @Mapping(source = "metadata.collectionPeriod", target = "collectionPeriod")
    @Mapping(source = "metadata.collectionTime", target = "collectionTime")
    @Mapping(source = "metadata.collectionDays", target = "collectionDays")
    @Mapping(target = "geometry", expression = "java(mapPolygonToCoords(document.getGeometry()))")
    UrbanGeometry toDomain(UrbanGeometryDocument document);

    UrbanGeometryResponseDTO toResponseDTO(UrbanGeometry domain);

    // Helper para converter GeoJsonPolygon para uma lista de coordenadas legível
    default List<List<Double[]>> mapPolygonToCoords(GeoJsonPolygon polygon) {
        if (polygon == null) return null;
        return polygon.getCoordinates().stream()
                .map(lineString -> lineString.getCoordinates().stream()
                        .map(p -> new Double[]{p.getX(), p.getY()})
                        .toList())
                .toList();
    }

    default List<List<List<Double>>> mapCoordinates(List<List<Double[]>> value) {
        if (value == null) return null;
        return value.stream()
                .map(list -> list.stream()
                        .map(Arrays::asList) // Converte Double[] para List<Double>
                        .toList())
                .toList();
    }
}
