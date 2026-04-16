package br.edu.fatecpg.deolhonolixo.core.gateway.city;

import br.edu.fatecpg.deolhonolixo.core.domain.city.Route;
import java.util.List;

public interface RouteGateway {
    List<Route> findAll();
    Route findById(Long id);
}
