package br.edu.fatecpg.deolhonolixo.core.domain;

import java.util.Date;

public record Truck(
    Long id,
    String licensePlate,
    String status,

    Date routeStart,
    Date routeEnd,
    String routeId
) { }
