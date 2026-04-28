package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckAlreadyRegisteredException;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckNotFoundException;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.HashMap;

@UseCase
public class TruckRegisterCase {
    private final TruckGateway truckGateway;

    public TruckRegisterCase(TruckGateway truckGateway) {
        this.truckGateway = truckGateway;
    }

    public HashMap<String,String> execute(Truck truck){
        try {
            truckGateway.existsBylicensePlate(truck);
        } catch (TruckNotFoundException e){
            return truckGateway.save(truck);
        }
        throw new TruckAlreadyRegisteredException();
    }
}
