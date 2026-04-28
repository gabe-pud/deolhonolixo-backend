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
public class TruckTelemetryData {
    @Field("speed_kmh")
    private Integer speedKmh;
}
