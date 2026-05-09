package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.gateway.EmailGateway;

import java.util.Map;

public class EmailRegisterCase {
    private final EmailGateway emailGateway;

    public EmailRegisterCase(EmailGateway emailGateway) {
        this.emailGateway = emailGateway;
    }

    public void execute(String subject, Map<String, Object> variables, User user) {
        emailGateway.sendEmail("confirm-email", subject, variables, user);
    }
}
