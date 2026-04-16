package br.edu.fatecpg.deolhonolixo.infrastructure.service;

import br.edu.fatecpg.deolhonolixo.core.domain.city.UrbanGeometry;
import br.edu.fatecpg.deolhonolixo.core.gateway.city.UrbanGeometryGateway;

import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.UrbanGeometryMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.UrbanGeometryMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UrbanGeometryService implements UrbanGeometryGateway {
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
    public UrbanGeometry findById(Long id) {
        return null;
    }

    @Override
    public UrbanGeometry findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Geometry não encontrada"));
    }
}
