package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.TruckHistory;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckGeolocationNotFoundException;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckHistoryGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.TruckHistoryMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.TruckHistoryDocument;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.TruckHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TruckHistoryPersistenceAdapter implements TruckHistoryGateway {
    private final TruckHistoryRepository repository;
    private final TruckHistoryMapper mapper;


    @Override
    public List<TruckHistory> findAll() {
        List<TruckHistoryDocument> docs = repository.findAll();

        return docs.stream().map(mapper::toDomainFromDocument).toList();
    }

    @Override
    public List<TruckHistory> findByLicencePlate(String licensePlate) {
        List<TruckHistoryDocument> docs = repository.findAllByLicensePlate(licensePlate);

        return docs.stream().map(mapper::toDomainFromDocument).toList();
    }

    @Override
    public TruckHistory getLastGeolocation(String licensePlate) {
        TruckHistoryDocument doc = repository.findFirstByLicensePlateOrderByTimestampDesc(licensePlate).orElseThrow(TruckGeolocationNotFoundException::new);
        return mapper.toDomainFromDocument(doc);
    }
}
