package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "routes")
public class RouteDocument {
    @Id
    private String id;
    @Field(name = "route_id")
    private String routeId;
    @Field(name = "route_name")
    private String routeName;
    private List<String> neighborhoods;
    @Field(name = "route_geometry")
    private Object routeGeometry;
}
