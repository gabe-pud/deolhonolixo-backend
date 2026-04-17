package br.edu.fatecpg.deolhonolixo.core.usecase.city;

import br.edu.fatecpg.deolhonolixo.core.domain.city.UrbanGeometry;
import br.edu.fatecpg.deolhonolixo.core.gateway.city.UrbanGeometryGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.List;

@UseCase
public class GetUrbanGeometryUseCase {
    private final UrbanGeometryGateway gateway;

    public GetUrbanGeometryUseCase(UrbanGeometryGateway gateway) {
        this.gateway = gateway;
    }

    public List<UrbanGeometry> execute() {
        return gateway.findAll();
    }

    public UrbanGeometry executeById(Long id) {
        return gateway.findById(id);
    }
}