package br.edu.fatecpg.deolhonolixo.infrastructure.mapper;

import br.edu.fatecpg.deolhonolixo.core.domain.TruckHistory;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckHistoryFindResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckHistoryGeolocationResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.TruckHistoryDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Mapper(componentModel = "spring")
public interface TruckHistoryMapper {
    @Mapping(target = "licensePlate", source = "licensePlate")
    @Mapping(target = "timestamp", source = "timestamp")
    @Mapping(target = "telemetry.speedKmh", source = "speedKmh")
    @Mapping(target = "position", source = "truckHistory")
    TruckHistoryDocument toDocumentFromDomain(TruckHistory truckHistory);

    @Mapping(target = "speedKmh", source = "telemetry.speedKmh")
    @Mapping(target = "longitude", expression = "java(doc.getPosition().getX())")
    @Mapping(target = "latitude", expression = "java(doc.getPosition().getY())")
    TruckHistory toDomainFromDocument(TruckHistoryDocument doc);

    @Mapping(target = "position.longitude", expression = "java(truckHistory.longitude())")
    @Mapping(target = "position.latitude", expression = "java(truckHistory.latitude())")
    @Mapping(target = "telemetry.speedKmh", source = "speedKmh")
    TruckHistoryFindResponseDTO toHistoryFindResponseDTO(TruckHistory truckHistory);

    @Mapping(target = "position.longitude", expression = "java(truckHistory.longitude())")
    @Mapping(target = "position.latitude", expression = "java(truckHistory.latitude())")
    TruckHistoryGeolocationResponseDTO toHistoryGeolocationResponseDTO(TruckHistory truckHistory);

    default GeoJsonPoint mapToGeoJsonPoint(TruckHistory truckHistory) {
        if (truckHistory== null || truckHistory.longitude() == null || truckHistory.latitude() == null) {
            return null;
        }
        return new GeoJsonPoint(truckHistory.longitude(), truckHistory.latitude());
    }
}
