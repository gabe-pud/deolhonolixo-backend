package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.LoginValidationException;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.UserNotFoundException;
import br.edu.fatecpg.deolhonolixo.core.gateway.UserGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.HashMap;

@UseCase
public class LoginCase {
    private final UserGateway userGateway;

    public LoginCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public HashMap<String, String> execute(User user){
        try {
            User loginValidationUser = userGateway.findByEmail(user);
            return userGateway.validateLogin(user, loginValidationUser);
        } catch (UserNotFoundException e){
            throw new LoginValidationException(e);
        }
    }
}
