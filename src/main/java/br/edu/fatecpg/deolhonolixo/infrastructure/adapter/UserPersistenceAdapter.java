package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.Role;
import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.gateway.UserGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.UserMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaEntity;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaRepository;
import br.edu.fatecpg.deolhonolixo.infrastructure.service.AuthService;
import br.edu.fatecpg.deolhonolixo.infrastructure.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

@Component
public class UserPersistenceAdapter implements UserGateway {
    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final EmailService emailService;

    public UserPersistenceAdapter(UserJpaRepository jpaRepository, UserMapper mapper, PasswordEncoder passwordEncoder, AuthService authService, EmailService emailService) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.emailService = emailService;
    }

    @Override
    public User save(User user) {
        UserJpaEntity newUser = buildUser(user);

        UserJpaEntity savedEntity = jpaRepository.save(newUser);
        emailService.sendVerificationEmail(mapper.toDomainFromJpa(savedEntity));
        return mapper.toDomainFromJpa(savedEntity);
    }

    @Override
    public Optional<User> findByEmail(User user) {
        Optional<UserJpaEntity> jpaEntity = jpaRepository.findByEmail(user.email());
        if (jpaEntity.isEmpty()) {
            return Optional.empty();
        }
        UserJpaEntity newUser = buildUser(user);
        return Optional.ofNullable(mapper.toDomainFromJpa(newUser));
    }

    public UserJpaEntity buildUser(User user){
        UserJpaEntity jpaEntity = new UserJpaEntity();

        jpaEntity.setPassword(passwordEncoder.encode(user.password()));
        jpaEntity.setEmail(user.email());
        jpaEntity.setUsername(user.username());

        String code = authService.generateCode();
        jpaEntity.setVerificationCode(code);
        jpaEntity.setVerificationExpiry(Instant.now().plus(1, ChronoUnit.HOURS));
        jpaEntity.setVerified(false);

        if (user.role() != null){
            jpaEntity.setRoles(user.role());
        } else {
            jpaEntity.setRoles(Set.of(Role.ROLE_USER));
        }
        return jpaEntity;
    }
}
