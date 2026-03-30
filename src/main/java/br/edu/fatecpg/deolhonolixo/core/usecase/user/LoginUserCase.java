package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.gateway.UserGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.HashMap;

@UseCase
public class LoginUserCase {
    private final UserGateway userGateway;

    public LoginUserCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public HashMap<String, String> execute(User user){
        User loginValidationUser = userGateway.findByEmail(user);

        return userGateway.validateLogin(user, loginValidationUser);
    }
}
