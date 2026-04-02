package br.edu.fatecpg.deolhonolixo.infrastructure.adapter;

import br.edu.fatecpg.deolhonolixo.core.domain.Role;
import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.gateway.UserGateway;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.UserMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaEntity;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.UserJpaRepository;
import br.edu.fatecpg.deolhonolixo.infrastructure.security.TokenService;
import br.edu.fatecpg.deolhonolixo.infrastructure.service.AuthService;
import br.edu.fatecpg.deolhonolixo.infrastructure.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class UserPersistenceAdapter implements UserGateway {
    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final EmailService emailService;
    private final TokenService tokenService;

    public UserPersistenceAdapter(UserJpaRepository jpaRepository, UserMapper mapper, PasswordEncoder passwordEncoder, AuthService authService, EmailService emailService, TokenService tokenService) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    @Override
    public HashMap<String, String> save(User user) {
        UserJpaEntity newUser = buildUser(user);

        jpaRepository.save(newUser);

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("username", newUser.getUsername());
        templateVariables.put("verificationCode", newUser.getVerificationCode());

        emailService.sendEmail("confirm-registration.html", templateVariables, mapper.toDomainFromJpa(newUser));

        HashMap<String,String> response = new HashMap<>();
        response.put("username",newUser.getUsername());
        response.put("token", "");
        return response;
    }

    @Override
    public User findByEmail(User user) {
        UserJpaEntity jpaEntity = jpaRepository.findByEmail(user.email()).orElseThrow(() -> new RuntimeException("User not found"));

        return mapper.toDomainFromJpa(jpaEntity);
    }

    @Override
    public HashMap<String, String> validateLogin(User user, User LoginValidationUser) {
        UserJpaEntity loginJpaEntity = mapper.toJpaFromDomain(LoginValidationUser);

        if(passwordEncoder.matches(user.password(), loginJpaEntity.getPassword())){
            String token = this.tokenService.generateToken(loginJpaEntity);
            HashMap<String,String> response = new HashMap<>();
            response.put("username",loginJpaEntity.getUsername());
            response.put("token", token);
            return response;
        }
        return null;
    }

    public UserJpaEntity buildUser(User user){
        UserJpaEntity jpaEntity = new UserJpaEntity();

        jpaEntity.setPassword(passwordEncoder.encode(user.password()));
        jpaEntity.setEmail(user.email());
        jpaEntity.setUsername(user.username().trim());

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
