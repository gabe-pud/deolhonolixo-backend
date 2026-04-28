package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.List;

@UseCase
public class TruckFindAllCase {
    private final TruckGateway truckGateway;

    public TruckFindAllCase(TruckGateway truckGateway) {
        this.truckGateway = truckGateway;
    }

    public List<Truck> execute(){
        return truckGateway.findAll();
    }
}
