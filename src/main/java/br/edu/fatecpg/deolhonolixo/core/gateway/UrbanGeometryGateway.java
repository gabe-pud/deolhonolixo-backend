package br.edu.fatecpg.deolhonolixo.core.gateway;

import br.edu.fatecpg.deolhonolixo.core.domain.UrbanGeometry;
import java.util.List;

public interface UrbanGeometryGateway {
    List<UrbanGeometry> findAll();
    UrbanGeometry findById(String id);


}