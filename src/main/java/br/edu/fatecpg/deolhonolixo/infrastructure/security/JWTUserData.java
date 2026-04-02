package br.edu.fatecpg.deolhonolixo.infrastructure.security;

import lombok.Builder;

import java.util.List;

@Builder
public record JWTUserData(
    long id,
    String email,
    List<String> roles
) { }
