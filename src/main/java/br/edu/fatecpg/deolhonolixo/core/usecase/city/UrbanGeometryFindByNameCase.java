package br.edu.fatecpg.deolhonolixo.core.usecase.city;

import br.edu.fatecpg.deolhonolixo.core.domain.UrbanGeometry;
import br.edu.fatecpg.deolhonolixo.core.gateway.UrbanGeometryGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

@UseCase
public class UrbanGeometryFindByNameCase {
    private final UrbanGeometryGateway gateway;

    public UrbanGeometryFindByNameCase(UrbanGeometryGateway gateway) {
        this.gateway = gateway;
    }

    public UrbanGeometry execute(String name) {
        return gateway.findByName(name);
    }
}
