package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.UserMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaEntity;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaRepository;
import br.edu.fatecpg.deolhonolixo.infrastructure.security.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

    @Mock
    UserJpaRepository repository;

    @Mock
    TokenService tokenService;

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    UserPersistenceAdapter adapter;

    @Test
    void save_encodesPasswordAndReturnsDomain() {
        adapter = new UserPersistenceAdapter(repository, mapper, encoder, tokenService);

        User input = new User(null, " alice ", "alice@example.com", "plainpass", null, false);

        UserJpaEntity saved = new UserJpaEntity();
        saved.setId(1L);
        saved.setUsername("alice");
        saved.setEmail("alice@example.com");
        saved.setPassword(encoder.encode("plainpass"));

        when(repository.save(any(UserJpaEntity.class))).thenReturn(saved);

        User result = adapter.save(input);

        assertNotNull(result);
        assertEquals("alice@example.com", result.email());
        assertEquals("alice", result.username());
        assertNotNull(result.password());
        assertFalse(result.verified());
        verify(repository).save(any(UserJpaEntity.class));
    }

    @Test
    void findByEmail_returnsDomain() {
        adapter = new UserPersistenceAdapter(repository, mapper, encoder, tokenService);

        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(2L);
        entity.setUsername("bob");
        entity.setEmail("bob@example.com");
        entity.setPassword(encoder.encode("x"));

        when(repository.findByEmail("bob@example.com")).thenReturn(Optional.of(entity));

        User u = adapter.findByEmail("bob@example.com");

        assertEquals("bob@example.com", u.email());
        assertEquals("bob", u.username());
    }

    @Test
    void existsByEmail_delegatesToRepository() {
        adapter = new UserPersistenceAdapter(repository, mapper, encoder, tokenService);

        when(repository.existsByEmail("x@y.z")).thenReturn(true);

        assertTrue(adapter.existsByEmail("x@y.z"));
        verify(repository).existsByEmail("x@y.z");
    }
}
