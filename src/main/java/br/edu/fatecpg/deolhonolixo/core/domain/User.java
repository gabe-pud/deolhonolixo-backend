package br.edu.fatecpg.deolhonolixo.core.domain;

import java.time.Instant;
import java.util.Set;

public record User(
    Long id,
    String username,
    String email,
    String password,
    Set<Role> role,

    String verificationCode,
    Boolean verified,
    Instant verificationExpiry,

    String passwordResetCode,
    Instant passwordResetExpiry
) { }
