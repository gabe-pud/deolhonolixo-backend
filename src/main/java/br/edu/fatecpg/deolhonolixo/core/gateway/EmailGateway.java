package br.edu.fatecpg.deolhonolixo.core.gateway;

import br.edu.fatecpg.deolhonolixo.core.domain.User;

import java.util.Map;

public interface EmailGateway {
    void sendEmail(String templateName, String subject, Map<String, Object> variables, User user);
}
