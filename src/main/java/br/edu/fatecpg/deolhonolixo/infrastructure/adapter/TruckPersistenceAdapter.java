package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckNotFoundException;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.TruckMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.TruckJpaEntity;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.TruckJpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TruckPersistenceAdapter implements TruckGateway {
    private final TruckJpaRepository jpaRepository;
    private final TruckMapper mapper;

    public TruckPersistenceAdapter(TruckJpaRepository jpaRepository, TruckMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public HashMap<String,String> save(Truck truck) {
        TruckJpaEntity truckJpa = mapper.toJpaFromDomain(truck);

        TruckJpaEntity newTruck = jpaRepository.save(truckJpa);
        HashMap<String,String> response = new HashMap<>();
        response.put("licensePlate", newTruck.getLicensePlate());
        return response;
    }

    @Override
    public HashMap<String, String> search(Truck truck) {
        TruckJpaEntity truckJpa = jpaRepository.findBylicensePlate(truck.licensePlate()).orElseThrow(TruckNotFoundException::new);
        HashMap<String,String> response = new HashMap<>();
        response.put("licensePlate", truckJpa.getLicensePlate());
        response.put("routeId", truckJpa.getRouteId());
        return response;
    }

    @Override
    public Truck findBylicensePlate(Truck truck) {
        TruckJpaEntity truckJpa = jpaRepository.findBylicensePlate(truck.licensePlate()).orElseThrow(TruckNotFoundException::new);
        return mapper.toDomainFromJpa(truckJpa);
    }

}
