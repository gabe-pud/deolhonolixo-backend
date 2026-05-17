package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.Role;
import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.LoginValidationException;
import br.edu.fatecpg.deolhonolixo.core.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginCaseTest {
    @Mock
    private UserGateway userGateway;
    @InjectMocks
    private LoginCase useCase;

    @Test
    void shouldReturnTokenWhenPasswordMatches() {
        String email = "ana@example.com";
        String password = "secret";
        User user = new User(1L, "Ana", email, "encoded-secret", Set.of(Role.ROLE_USER), true);

        when(userGateway.findByEmail(email)).thenReturn(user);
        when(userGateway.passwordMatches(password, user.password())).thenReturn(true);
        when(userGateway.generateToken(user)).thenReturn("jwt-token");

        LoginCaseOutputDTO result = useCase.execute(email, password);

        assertEquals("Ana", result.username());
        assertEquals("jwt-token", result.token());
        verify(userGateway).findByEmail(email);
        verify(userGateway).passwordMatches(password, user.password());
        verify(userGateway).generateToken(user);
    }

    @Test
    void shouldThrowWhenPasswordDoesNotMatch() {
        String email = "ana@example.com";
        String password = "wrong";
        User user = new User(1L, "Ana", email, "encoded-secret", Set.of(Role.ROLE_USER), true);

        when(userGateway.findByEmail(email)).thenReturn(user);
        when(userGateway.passwordMatches(password, user.password())).thenReturn(false);

        assertThrows(LoginValidationException.class, () -> useCase.execute(email, password));
        verify(userGateway).findByEmail(email);
        verify(userGateway).passwordMatches(password, user.password());
        verify(userGateway, never()).generateToken(user);
    }
}