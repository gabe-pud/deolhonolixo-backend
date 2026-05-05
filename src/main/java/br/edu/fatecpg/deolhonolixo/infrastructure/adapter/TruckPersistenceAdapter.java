package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckNotFoundException;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.TruckMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.TruckJpaEntity;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.TruckJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TruckPersistenceAdapter implements TruckGateway {
    private final TruckJpaRepository jpaRepository;
    private final TruckMapper mapper;


    @Override
    public HashMap<String,String> save(Truck truck) {
        TruckJpaEntity truckJpa = mapper.toJpaFromDomain(truck);

        TruckJpaEntity newTruck = jpaRepository.save(truckJpa);
        HashMap<String,String> response = new HashMap<>();
        response.put("licensePlate", newTruck.getLicensePlate());
        return response;
    }

    @Override
    public Truck findBylicensePlate(String licensePlate) {
        TruckJpaEntity truckJpa = jpaRepository.findBylicensePlate(licensePlate).orElseThrow(TruckNotFoundException::new);
        return mapper.toDomainFromJpa(truckJpa);
    }

    @Override
    public void existsBylicensePlate(Truck truck) {
        jpaRepository.findBylicensePlate(truck.licensePlate()).orElseThrow(TruckNotFoundException::new);
    }

    @Override
    public Truck findById(Long id) {
        TruckJpaEntity truckJpa = jpaRepository.findById(id).orElseThrow(TruckNotFoundException::new);
        return mapper.toDomainFromJpa(truckJpa);
    }

    @Override
    public List<Truck> findAll() {
        List<TruckJpaEntity> trucks = jpaRepository.findAll();
        return trucks.stream().map(mapper::toDomainFromJpa).toList();
    }

}
