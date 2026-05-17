package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.Route;
import br.edu.fatecpg.deolhonolixo.core.gateway.RouteGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.RouteMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.RouteMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoutePersistenceAdapter implements RouteGateway {
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
    public Route findById(String id) {
        return repository.findById(String.valueOf(id))
                .map(mapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Rota não encontrada"));
    }

    @Override
    public Route findByRouteId(String id) {
        return mapper.toDomain(repository.findOneByRouteId(id));
    }
}