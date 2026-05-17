package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserJpaRepositoryTest {

    @Mock
    UserJpaRepository repository;

    @Test
    void saveAndFindByEmail_contract() {
        UserJpaEntity e = new UserJpaEntity();
        e.setId(100L);
        e.setUsername("intuser");
        e.setEmail("intuser@example.com");
        e.setPassword("p");

        when(repository.save(any(UserJpaEntity.class))).thenReturn(e);
        when(repository.findByEmail("intuser@example.com")).thenReturn(Optional.of(e));
        when(repository.existsByEmail("intuser@example.com")).thenReturn(true);

        var saved = repository.save(new UserJpaEntity());
        assertEquals(100L, saved.getId());

        var found = repository.findByEmail("intuser@example.com");
        assertTrue(found.isPresent());
        assertEquals("intuser", found.get().getUsername());
        assertTrue(repository.existsByEmail("intuser@example.com"));
    }
}
