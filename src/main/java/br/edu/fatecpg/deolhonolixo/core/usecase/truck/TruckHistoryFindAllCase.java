package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.TruckHistory;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckHistoryGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.List;

@UseCase
public class TruckHistoryFindAllCase {
    private final TruckHistoryGateway truckHistoryGateway;

    public TruckHistoryFindAllCase(TruckHistoryGateway truckHistoryGateway) {
        this.truckHistoryGateway = truckHistoryGateway;
    }

    public List<TruckHistory> execute(){
        return truckHistoryGateway.findAll();
    }
}
