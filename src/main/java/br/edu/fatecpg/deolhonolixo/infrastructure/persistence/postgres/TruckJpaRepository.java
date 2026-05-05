package br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TruckJpaRepository extends JpaRepository<TruckJpaEntity, Long> {
    Optional<TruckJpaEntity> findBylicensePlate(String licensePlate);
}
