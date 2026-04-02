package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.gateway.UserGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.HashMap;

@UseCase
public class RegisterUserCase {
    private final UserGateway userGateway;

    public RegisterUserCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public HashMap<String, String> execute(User user){
        try {
            userGateway.findByEmail(user);
        } catch (RuntimeException e) {
            return userGateway.save(user);
        }
        return null;
    }
}
