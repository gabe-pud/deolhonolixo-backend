package br.edu.fatecpg.deolhonolixo.core.domain;

import java.time.Instant;

public record TruckHistory(
        String id,
        Instant timestamp,
        String licensePlate,
        Double longitude,
        Double latitude,
        Integer speedKmh
) {}
