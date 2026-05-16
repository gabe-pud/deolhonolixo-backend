package br.edu.fatecpg.deolhonolixo.infrastructure.exception;

import java.time.Instant;

public record ErrorResponseDTO(
        Instant timestamp,
        int status,
        String message
) { }
