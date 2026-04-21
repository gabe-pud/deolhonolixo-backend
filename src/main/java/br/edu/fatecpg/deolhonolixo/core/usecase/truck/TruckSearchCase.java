package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.HashMap;

@UseCase
public class TruckSearchCase {
    private final TruckGateway truckGateway;

    public TruckSearchCase(TruckGateway truckGateway) {
        this.truckGateway = truckGateway;
    }

    public HashMap<String,String> execute(Truck truck){
        return truckGateway.search(truck);
    }
}
