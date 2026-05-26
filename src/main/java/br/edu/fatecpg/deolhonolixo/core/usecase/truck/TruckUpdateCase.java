package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

@UseCase
public class TruckUpdateCase {
    private final TruckGateway truckGateway;

    public TruckUpdateCase(TruckGateway truckGateway) {
        this.truckGateway = truckGateway;
    }

    public Truck execute(String licensePlate, Truck truck) {
        Truck existingTruck = truckGateway.findBylicensePlate(licensePlate);
        Truck updatedTruck = new Truck(
                existingTruck.id(),
                existingTruck.licensePlate(),
                truck.status() != null ? truck.status() : existingTruck.status(),
                truck.routeStart() != null ? truck.routeStart() : existingTruck.routeStart(),
                truck.routeEnd() != null ? truck.routeEnd() : existingTruck.routeEnd(),
                truck.routeId() != null ? truck.routeId() : existingTruck.routeId()
        );

        truckGateway.save(updatedTruck);
        return updatedTruck;
    }
}