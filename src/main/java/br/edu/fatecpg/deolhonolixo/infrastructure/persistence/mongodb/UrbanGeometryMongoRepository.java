package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrbanGeometryMongoRepository extends MongoRepository<UrbanGeometryDocument, String> {
    UrbanGeometryDocument findOneByName(String id);
}