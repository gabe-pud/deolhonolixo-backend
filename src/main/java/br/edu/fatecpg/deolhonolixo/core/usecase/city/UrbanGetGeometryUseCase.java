package br.edu.fatecpg.deolhonolixo.core.usecase.city;

import br.edu.fatecpg.deolhonolixo.core.domain.UrbanGeometry;
import br.edu.fatecpg.deolhonolixo.core.gateway.UrbanGeometryGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.List;

@UseCase
public class UrbanGetGeometryUseCase {
    private final UrbanGeometryGateway gateway;

    public UrbanGetGeometryUseCase(UrbanGeometryGateway gateway) {
        this.gateway = gateway;
    }

    public List<UrbanGeometry> execute() {
        return gateway.findAll();
    }

    public UrbanGeometry executeById(String id) {
        return gateway.findById(id);
    }
}