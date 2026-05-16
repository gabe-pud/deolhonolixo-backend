package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.Role;
import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.UserNotFoundException;
import br.edu.fatecpg.deolhonolixo.core.gateway.UserGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.UserMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaEntity;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaRepository;
import br.edu.fatecpg.deolhonolixo.infrastructure.security.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserPersistenceAdapter implements UserGateway {
    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserPersistenceAdapter(UserJpaRepository jpaRepository, UserMapper mapper, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public User save(User user) {
        UserJpaEntity newUser = buildUser(user);
        UserJpaEntity savedUser = jpaRepository.save(newUser);

        return mapper.toDomainFromJpa(savedUser);
    }

    @Override
    public User findByEmail(String email) {
        UserJpaEntity targetUser = jpaRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return mapper.toDomainFromJpa(targetUser);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public String generateToken(User user) {
        UserJpaEntity loginUser = mapper.toJpaFromDomain(user);
        return tokenService.generateToken(loginUser);
    }

    @Override
    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public UserJpaEntity buildUser(User user){
        UserJpaEntity jpaEntity = new UserJpaEntity();

        jpaEntity.setPassword(passwordEncoder.encode(user.password()));
        jpaEntity.setEmail(user.email());
        jpaEntity.setUsername(user.username().trim());

        jpaEntity.setVerified(false);

        if (user.role() != null){
            jpaEntity.setRoles(user.role());
        } else {
            jpaEntity.setRoles(Set.of(Role.ROLE_USER));
        }

        return jpaEntity;
    }
}
