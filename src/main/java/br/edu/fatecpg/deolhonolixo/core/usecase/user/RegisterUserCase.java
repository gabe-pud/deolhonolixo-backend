package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.gateway.UserGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.Optional;

@UseCase
public class RegisterUserCase {
    private final UserGateway userGateway;

    public RegisterUserCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(User user){
        Optional<User> newUser = userGateway.findByEmail(user);

        if (newUser.isEmpty()){
            return userGateway.save(user);
        }
        return null;
    }
}
