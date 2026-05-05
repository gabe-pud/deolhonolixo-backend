package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

@UseCase
public class TruckFindBylicensePlateCase {
    private final TruckGateway truckGateway;

    public TruckFindBylicensePlateCase(TruckGateway truckGateway) {
        this.truckGateway = truckGateway;
    }

    public Truck execute(String licensePlate){
        return truckGateway.findBylicensePlate(licensePlate);
    }
}
