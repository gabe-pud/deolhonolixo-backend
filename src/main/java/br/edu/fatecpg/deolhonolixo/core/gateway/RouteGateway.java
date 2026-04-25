package br.edu.fatecpg.deolhonolixo.core.gateway;

import br.edu.fatecpg.deolhonolixo.core.domain.Route;
import java.util.List;

public interface RouteGateway {
    List<Route> findAll();
    Route findById(String id);
}
