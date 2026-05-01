package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrbanGeometryMetadata {
    @Field(name = "collection_period")
    private String collectionPeriod;

    @Field(name = "collection_time")
    private String collectionTime;

    @Field(name = "collection_days")
    private List<String> collectionDays;

}
