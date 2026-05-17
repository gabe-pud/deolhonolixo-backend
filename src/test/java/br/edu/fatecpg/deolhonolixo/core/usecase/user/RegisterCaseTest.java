package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.Role;
import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.UserAlreadyRegisteredException;
import br.edu.fatecpg.deolhonolixo.core.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterCaseTest {
    @Mock
    private UserGateway userGateway;
    @Mock
    private EmailRegisterCase emailRegisterCase;
    @InjectMocks
    private RegisterCase useCase;

    @Test
    void shouldSaveUserWhenEmailDoesNotExist() {
        User user = new User(null, "Ana", "ana@example.com", "secret", Set.of(Role.ROLE_USER), false);

        when(userGateway.existsByEmail(user.email())).thenReturn(false);
        when(userGateway.save(user)).thenReturn(user);

        User result = useCase.execute(user);

        assertSame(user, result);
        verify(userGateway).existsByEmail(user.email());
        verify(userGateway).save(user);
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        User user = new User(null, "Ana", "ana@example.com", "secret", Set.of(Role.ROLE_USER), false);

        when(userGateway.existsByEmail(user.email())).thenReturn(true);

        assertThrows(UserAlreadyRegisteredException.class, () -> useCase.execute(user));
        verify(userGateway).existsByEmail(user.email());
        verify(userGateway, never()).save(any());
    }
}