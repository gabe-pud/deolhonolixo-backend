package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TruckHistoryRepository extends MongoRepository<TruckHistoryDocument,String> {
    List<TruckHistoryDocument> findAllByLicensePlate(String licensePlate);
    Optional<TruckHistoryDocument> findFirstByLicensePlateOrderByTimestampDesc(String licensePlate);
}
