package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb;


import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "urban_geometry")
public class UrbanGeometryDocument {
    @Id
    private String id;
    private String type;
    private String name;
    private String city;
    private GeoJsonPolygon geometry; // Mapeia o objeto "geometry" automaticamente
    private UrbanGeometryMetadata metadata;
}
