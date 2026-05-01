package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RouteMongoRepository extends MongoRepository<RouteDocument, String> {
    RouteDocument findOneByRouteId(String id);
}
