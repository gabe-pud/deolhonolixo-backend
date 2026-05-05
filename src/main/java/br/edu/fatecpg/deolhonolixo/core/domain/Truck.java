package br.edu.fatecpg.deolhonolixo.core.domain;

import java.time.LocalDateTime;

public record Truck(
    Long id,
    String licensePlate,
    String status,

    LocalDateTime routeStart,
    LocalDateTime routeEnd,
    String routeId
) { }
