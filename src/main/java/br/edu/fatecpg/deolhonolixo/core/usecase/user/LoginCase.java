package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.LoginValidationException;
import br.edu.fatecpg.deolhonolixo.core.gateway.UserGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

@UseCase
public class LoginCase {
    private final UserGateway userGateway;

    public LoginCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public LoginCaseOutputDTO execute(String email, String rawInputPassword){
        User user = userGateway.findByEmail(email);

        if(!userGateway.passwordMatches(rawInputPassword, user.password())) {
            throw new LoginValidationException();
        }

        String token = userGateway.generateToken(user);

        return new LoginCaseOutputDTO(user.username(), token);
    }
}
