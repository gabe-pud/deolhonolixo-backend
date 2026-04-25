package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.UrbanGeometry;
import br.edu.fatecpg.deolhonolixo.core.gateway.UrbanGeometryGateway;

import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.UrbanGeometryMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.UrbanGeometryMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UrbanGeometryPersistenceAdapter implements UrbanGeometryGateway {
    private final UrbanGeometryMongoRepository repository;
    private final UrbanGeometryMapper mapper;

    @Override
    public List<UrbanGeometry> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public UrbanGeometry findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Geometry não encontrada"));
    }
}
