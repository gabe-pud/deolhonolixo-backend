package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "telemetry_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TruckHistoryDocument {
    @Id
    private String id;

    private Instant timestamp;

    @Field("license_plate")
    private String licensePlate;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint position;

    private TruckTelemetryData telemetry;
}
