package br.edu.fatecpg.deolhonolixo.core.gateway;

import br.edu.fatecpg.deolhonolixo.core.domain.TruckHistory;

import java.util.List;

public interface TruckHistoryGateway {
    List<TruckHistory> findAll();
    List<TruckHistory> findByLicencePlate(String licensePlate);
    TruckHistory getLastGeolocation(String licensePlate);
}
