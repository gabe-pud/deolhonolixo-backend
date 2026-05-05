package br.edu.fatecpg.deolhonolixo.core.gateway;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;

import java.util.HashMap;
import java.util.List;

public interface TruckGateway {
    HashMap<String,String> save(Truck truck);
    Truck findBylicensePlate(String licensePlate);
    void existsBylicensePlate(Truck truck);
    Truck findById(Long id);
    List<Truck> findAll();
}
