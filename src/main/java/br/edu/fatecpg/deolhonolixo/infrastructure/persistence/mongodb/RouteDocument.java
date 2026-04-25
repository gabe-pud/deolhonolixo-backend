package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "routes")
public class RouteDocument {
    @Id
    private String id;
    private String routeId;
    private String routeName;
    private List<String> neighborhoods;
    private Object routeGeometry;
}
