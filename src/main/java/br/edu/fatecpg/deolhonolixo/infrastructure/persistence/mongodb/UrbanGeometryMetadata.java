package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrbanGeometryMetadata {
    @Field(name = "estimated_population")
    private Integer estimatedPopulation;
    @Field(name = "collection_frequency")
    private String collectionFrequency;
}
