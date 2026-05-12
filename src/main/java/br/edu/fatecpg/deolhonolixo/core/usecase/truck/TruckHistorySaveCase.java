package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.TruckHistory;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckHistoryGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.time.Instant;

@UseCase
public class TruckHistorySaveCase {
    private final TruckHistoryGateway truckHistoryGateway;

    public TruckHistorySaveCase(TruckHistoryGateway truckHistoryGateway) {
        this.truckHistoryGateway = truckHistoryGateway;
    }

    public TruckHistory execute(String licensePlate, Double longitude, Double latitude) {
        TruckHistory truckHistory = new TruckHistory(null, Instant.now(), licensePlate, longitude, latitude, 0);
        return truckHistoryGateway.saveGeolocation(truckHistory);
    }
}
