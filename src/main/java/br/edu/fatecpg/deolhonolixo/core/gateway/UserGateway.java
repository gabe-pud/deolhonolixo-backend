package br.edu.fatecpg.deolhonolixo.core.gateway;

import br.edu.fatecpg.deolhonolixo.core.domain.User;

public interface UserGateway {
    User findByEmail(String email);
    User save(User user);
    boolean existsByEmail(String email);

    String generateToken(User user);
    boolean passwordMatches(String rawPassword, String encodedPassword);
}
