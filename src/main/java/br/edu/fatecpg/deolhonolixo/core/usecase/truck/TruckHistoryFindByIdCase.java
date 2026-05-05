package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.domain.TruckHistory;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckHistoryGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.List;

@UseCase
public class TruckHistoryFindByIdCase {
    private final TruckHistoryGateway truckHistoryGateway;
    private final TruckGateway truckGateway;

    public TruckHistoryFindByIdCase(TruckHistoryGateway truckHistoryGateway, TruckGateway truckGateway) {
        this.truckHistoryGateway = truckHistoryGateway;
        this.truckGateway = truckGateway;
    }

    public List<TruckHistory> execute(Long id){
        Truck truck = truckGateway.findById(id);
        return truckHistoryGateway.findByLicencePlate(truck.licensePlate());
    }
}
