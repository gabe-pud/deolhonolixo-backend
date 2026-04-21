package br.edu.fatecpg.deolhonolixo.core.gateway;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;

import java.util.HashMap;

public interface TruckGateway {
    HashMap<String,String> save(Truck truck);
    HashMap<String,String> search(Truck truck);
    Truck findBylicensePlate(Truck truck);
}
