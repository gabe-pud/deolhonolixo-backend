package br.edu.fatecpg.deolhonolixo.infrastructure.mapper;

import br.edu.fatecpg.deolhonolixo.core.domain.Role;
import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.usecase.user.LoginCaseOutputDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.UserLoginRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.UserRegisterRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.UserLoginAndRegisterResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest {
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapDomainToJpaEntity() {
        User user = new User(3L, "Ana", "ana@example.com", "secret", Set.of(Role.ROLE_USER, Role.ROLE_ADMIN), true);

        UserJpaEntity result = mapper.toJpaFromDomain(user);

        assertAll(
                () -> assertEquals(user.id(), result.getId()),
                () -> assertEquals(user.username(), result.getUsername()),
                () -> assertEquals(user.email(), result.getEmail()),
                () -> assertEquals(user.password(), result.getPassword()),
                () -> assertEquals(user.role(), result.getRoles()),
                () -> assertEquals(user.verified(), result.getVerified())
        );
    }

    @Test
    void shouldMapJpaEntityToDomain() {
        UserJpaEntity entity = new UserJpaEntity(3L, "Ana", "ana@example.com", "secret", Set.of(Role.ROLE_USER), true);

        User result = mapper.toDomainFromJpa(entity);

        assertAll(
                () -> assertEquals(entity.getId(), result.id()),
                () -> assertEquals(entity.getUsername(), result.username()),
                () -> assertEquals(entity.getEmail(), result.email()),
                () -> assertEquals(entity.getPassword(), result.password()),
                () -> assertEquals(entity.getRoles(), result.role()),
                () -> assertEquals(entity.getVerified(), result.verified())
        );
    }

    @Test
    void shouldMapRegisterRequestToDomain() {
        UserRegisterRequestDTO dto = new UserRegisterRequestDTO(
                "Ana",
                "ana@example.com",
                "secret123!",
                "secret123!",
                Set.of(Role.ROLE_USER)
        );

        User result = mapper.toDomainFromRegisterRequestDto(dto);

        assertAll(
                () -> assertNull(result.id()),
                () -> assertEquals(dto.username(), result.username()),
                () -> assertEquals(dto.email(), result.email()),
                () -> assertEquals(dto.password(), result.password()),
                () -> assertEquals(dto.role(), result.role()),
                () -> assertNull(result.verified())
        );
    }

    @Test
    void shouldMapLoginRequestToDomain() {
        UserLoginRequestDTO dto = new UserLoginRequestDTO("ana@example.com", "secret123!");

        User result = mapper.toDomainFromLoginRequestDTO(dto);

        assertAll(
                () -> assertNull(result.id()),
                () -> assertNull(result.username()),
                () -> assertEquals(dto.email(), result.email()),
                () -> assertEquals(dto.password(), result.password()),
                () -> assertNull(result.role()),
                () -> assertNull(result.verified())
        );
    }

    @Test
    void shouldMapDomainToRegisterResponseDto() {
        User user = new User(3L, "Ana", "ana@example.com", "secret", Set.of(Role.ROLE_USER), true);

        UserLoginAndRegisterResponseDTO result = mapper.toRegisterResponseDto(user);

        assertEquals(user.username(), result.username());
        assertNull(result.token());
    }

    @Test
    void shouldMapLoginOutputToResponseDto() {
        LoginCaseOutputDTO dto = new LoginCaseOutputDTO("Ana", "jwt-token");

        UserLoginAndRegisterResponseDTO result = mapper.toLoginResponseDto(dto);

        assertAll(
                () -> assertEquals(dto.username(), result.username()),
                () -> assertEquals(dto.token(), result.token())
        );
    }
}