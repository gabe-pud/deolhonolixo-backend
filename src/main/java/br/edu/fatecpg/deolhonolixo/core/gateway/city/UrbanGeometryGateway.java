package br.edu.fatecpg.deolhonolixo.core.gateway.city;

import br.edu.fatecpg.deolhonolixo.core.domain.city.UrbanGeometry;
import java.util.List;

public interface UrbanGeometryGateway {
    List<UrbanGeometry> findAll();
    UrbanGeometry findById(Long id);

    UrbanGeometry findById(String id);
}