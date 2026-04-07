package br.edu.fatecpg.deolhonolixo.core.domain.city;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    private String id;
    private String routeId;
    private String routeName;
    private List<String> neighborhoods;
    private Object routeGeometry;
}

