package br.edu.fatecpg.deolhonolixo.infrastructure.security;

import br.edu.fatecpg.deolhonolixo.core.domain.Role;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {
    private TokenService tokenService;

    @BeforeEach
    void setup() throws Exception {
        tokenService = new TokenService();
        Field secretField = TokenService.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(tokenService, "test-secret-123");
    }

    @AfterEach
    void tearDown() {
        // nothing
    }

    @Test
    void shouldGenerateAndValidateToken() {
        UserJpaEntity user = new UserJpaEntity(5L, "Ana", "ana@example.com", "pwd", Set.of(Role.ROLE_USER), true);

        String token = tokenService.generateToken(user);

        assertNotNull(token);

        Optional<JWTUserData> opt = tokenService.validateToken(token);
        assertTrue(opt.isPresent());

        JWTUserData data = opt.get();
        assertEquals(user.getId().longValue(), data.id());
        assertEquals(user.getEmail(), data.email());
        assertEquals(1, data.roles().size());
        assertEquals("ROLE_USER", data.roles().get(0));
    }

    @Test
    void shouldReturnEmptyWhenTokenInvalid() {
        Optional<JWTUserData> opt = tokenService.validateToken("invalid.token.here");
        assertTrue(opt.isEmpty());
    }
}
