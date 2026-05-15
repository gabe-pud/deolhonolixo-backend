package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.gateway.EmailGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

import java.util.Map;

@UseCase
public class EmailRegisterCase {
    private final EmailGateway emailGateway;

    public EmailRegisterCase(EmailGateway emailGateway) {
        this.emailGateway = emailGateway;
    }

    public void execute(String templateName, String subject, Map<String, Object> variables, User user) {
        emailGateway.sendEmail(templateName, subject, variables, user);
    }
}
