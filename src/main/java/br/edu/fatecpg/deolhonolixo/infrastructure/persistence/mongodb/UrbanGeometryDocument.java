package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

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
    private List<Object> geometry;
    private Map<String, Object> metadata;
}
