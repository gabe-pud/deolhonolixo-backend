package br.edu.fatecpg.deolhonolixo.core.domain;

import java.util.Set;

public record User(
    Long id,
    String username,
    String email,
    String password,
    Set<Role> role,

    Boolean verified
) { }
