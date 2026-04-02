package br.edu.fatecpg.deolhonolixo.core.gateway;

import br.edu.fatecpg.deolhonolixo.core.domain.User;

import java.util.HashMap;

public interface UserGateway {
    User findByEmail(User user);
    HashMap<String, String> save(User user);
    HashMap<String, String> validateLogin(User user, User LoginValidationUser);
}
