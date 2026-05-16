package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.UserAlreadyRegisteredException;
import br.edu.fatecpg.deolhonolixo.core.gateway.UserGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

@UseCase
public class RegisterCase {
    private final UserGateway userGateway;
    private final EmailRegisterCase emailRegisterCase;

    public RegisterCase(UserGateway userGateway, EmailRegisterCase emailRegisterCase) {
        this.userGateway = userGateway;
        this.emailRegisterCase = emailRegisterCase;
    }

    public User execute(User user){
        if(userGateway.existsByEmail(user.email())) {
            throw new UserAlreadyRegisteredException();
        }

        // Map<String, Object> templateVariables = new HashMap<>();
        // templateVariables.put("username", user.username());
        // o código relacionado ao Redis vem aqui posteriormente

        // emailRegisterCase.execute("confirm-registration.html", "De Olho No Lixo - Confirme o seu e-mail", templateVariables, user);

        return userGateway.save(user);
    }
}
