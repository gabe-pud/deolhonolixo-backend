package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.domain.TruckHistory;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckHistoryGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

@UseCase
public class TruckHistoryGetLastGeolocationCase {
    private final TruckHistoryGateway truckHistoryGateway;
    private final TruckGateway truckGateway;

    public TruckHistoryGetLastGeolocationCase(TruckHistoryGateway truckHistoryGateway, TruckGateway truckGateway) {
        this.truckHistoryGateway = truckHistoryGateway;
        this.truckGateway = truckGateway;
    }

    public TruckHistory execute(Long id){
        Truck truck = truckGateway.findById(id);
        return truckHistoryGateway.getLastGeolocation(truck.licensePlate());
    }
}
