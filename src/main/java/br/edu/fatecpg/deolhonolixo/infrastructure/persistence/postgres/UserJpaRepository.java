package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity,Long> {
    Optional<UserJpaEntity> findByEmail(String email);
    Optional<UserJpaEntity> findByVerificationCode(String code);
    Optional<UserJpaEntity> findByUsername(String username);
    Optional<UserJpaEntity> findByPasswordResetCode(String code);
}
