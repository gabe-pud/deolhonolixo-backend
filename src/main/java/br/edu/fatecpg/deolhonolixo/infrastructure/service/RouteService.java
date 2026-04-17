package br.edu.fatecpg.deolhonolixo.infrastructure.service;

import br.edu.fatecpg.deolhonolixo.core.domain.city.Route;
import br.edu.fatecpg.deolhonolixo.core.gateway.city.RouteGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.RouteMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.RouteMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService implements RouteGateway {
    private final RouteMongoRepository repository;
    private final RouteMapper mapper;

    @Override
    public List<Route> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }


    @Override
    public Route findById(Long id) {
        return repository.findById(String.valueOf(id))
                .map(mapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Rota não encontrada"));
    }
}