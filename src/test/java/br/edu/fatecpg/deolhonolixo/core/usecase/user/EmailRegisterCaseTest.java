package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.Role;
import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.gateway.EmailGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailRegisterCaseTest {
    @Mock
    private EmailGateway emailGateway;
    @InjectMocks
    private EmailRegisterCase useCase;

    @Test
    void shouldSendEmailWithProvidedData() {
        User user = new User(1L, "Ana", "ana@example.com", "secret", Set.of(Role.ROLE_USER), false);
        Map<String, Object> variables = Map.of("username", user.username());

        useCase.execute("confirm-registration.html", "Confirm your email", variables, user);

        verify(emailGateway).sendEmail("confirm-registration.html", "Confirm your email", variables, user);
    }
}