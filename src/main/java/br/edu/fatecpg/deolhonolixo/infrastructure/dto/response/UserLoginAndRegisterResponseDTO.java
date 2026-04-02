package br.edu.fatecpg.deolhonolixo.infrastructure.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginAndRegisterResponseDTO", description = "DTO de resposta enviado após login ou registro do usuário, contendo nome e token JWT")
public record UserLoginAndRegisterResponseDTO(
        @Schema(description = "Nome do usuário", example = "exemplo")
        String username,

        @Schema(description = "Token JWT para autenticação nas próximas requisições", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token
) { }
