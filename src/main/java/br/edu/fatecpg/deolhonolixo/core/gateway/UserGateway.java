package br.edu.fatecpg.deolhonolixo.core.gateway;

import br.edu.fatecpg.deolhonolixo.core.domain.User;

import java.util.Optional;

public interface UserGateway {
    User save(User user);
    Optional<User> findByEmail(User user);
}
