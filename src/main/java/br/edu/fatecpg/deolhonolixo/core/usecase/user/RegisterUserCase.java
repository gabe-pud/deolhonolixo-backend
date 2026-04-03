package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.UserAlreadyRegisteredException;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.UserNotFoundException;
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
        } catch (UserNotFoundException e) {
            return userGateway.save(user);
        }
        throw new UserAlreadyRegisteredException();
    }
}
