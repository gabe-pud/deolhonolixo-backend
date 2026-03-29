package br.edu.fatecpg.deolhonolixo.infrastructure.dto.request;

import br.edu.fatecpg.deolhonolixo.core.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Schema(name = "RegisterRequestDTO", description = "DTO para registro de um novo usuário")
public record UserRegisterRequestDTO(
        @Schema(description = "Nome de usuário único", example = "exemplo")
        @NotBlank(message = "O nome de usuário é obrigatório.")
        String username,

        @Schema(description = "E-mail do usuário", example = "usuario@exemplo.com")
        @Size(max = 255)
        @Email
        String email,

        @Schema(
                description = "Senha do usuário. Deve ter no mínimo 8 caracteres, conter letras, números e pelo menos um caractere especial.",
                example = "SenhaForte123!"
        )
        @Size(min = 8, max = 64,  message = "A senha deve ter no mínimo 8 caracteres e no máximo 64 caracteres.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&.])[A-Za-z\\d@$!%*#?&.]{8,}$",
                message = "A senha deve conter letras, números e caracteres especiais."
        )
        String password,

        @Schema(
                description = "Confirmação da senha do usuário. Deve ter no mínimo 8 caracteres, conter letras, números e pelo menos um caractere especial.",
                example = "SenhaForte123!"
        )
        @Size(min = 8, max = 64,  message = "A senha deve ter no mínimo 8 caracteres e no máximo 64 caracteres.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&.])[A-Za-z\\d@$!%*#?&.]{8,}$",
                message = "A senha deve conter letras, números e caracteres especiais."
        )
        String confirmPassword,

        Set<Role> role
) { }
