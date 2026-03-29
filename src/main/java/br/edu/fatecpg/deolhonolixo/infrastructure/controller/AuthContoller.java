package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.usecase.user.RegisterUserCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.UserRegisterRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.UserLoginAndRegisterResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Autenticação",
        description = "Endpoints responsáveis pela autenticação, registro e verificação de usuários."
)
public class AuthContoller {
    private final UserMapper userMapper;
    private final RegisterUserCase registerUserCase;

    @PostMapping("/register")
    @Operation(
            summary = "Registra um novo usuário",
            description = "Cria um novo usuário no sistema, envia o código de verificação por e-mail e retorna uma mensagem de confirmação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro realizado com sucesso, verifique seu e-mail"),
            @ApiResponse(responseCode = "400", description = "nome de usuário ou email já cadastrado")
    })
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequestDTO body){
        User userDomain = userMapper.toDomainFromRegisterRequestDto(body);

        User savedEntity = registerUserCase.execute(userDomain);

        if (savedEntity != null){
            return ResponseEntity.status(200).body(userMapper.toLoginRegisterResponseDto(savedEntity));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/teste-user")
    public ResponseEntity<?> testeUser(){
        return ResponseEntity.ok().body("usuaio comum e admin podem usar este endpoint");
    }

    @GetMapping("/teste-admin")
    public ResponseEntity<?> testeAdmin(){
        return ResponseEntity.ok().body("apenas admin pode usar este endpoint");
    }
}
