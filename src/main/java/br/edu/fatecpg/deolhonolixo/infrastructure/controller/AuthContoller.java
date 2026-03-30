package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.usecase.user.LoginUserCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.user.RegisterUserCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.UserLoginRequestDTO;
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

import java.util.HashMap;
import java.util.Optional;

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
    private final LoginUserCase loginUserCase;

    @PostMapping("/register")
    @Operation(
            summary = "Registra um novo usuário",
            description = "Cria um novo usuário no sistema, envia o código de verificação por e-mail e retorna uma mensagem de confirmação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro realizado com sucesso, verifique seu e-mail"),
            @ApiResponse(responseCode = "409", description = "nome de usuário ou email já cadastrado"),
            @ApiResponse(responseCode = "422", description = "A confirmação de senha não confere com a senha digitada")
    })
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequestDTO body){
        if (!body.password().equals(body.confirmPassword())){
            ResponseEntity.status(422).body("A confirmação de senha não confere com a senha digitada");
        }
        User userDomain = userMapper.toDomainFromRegisterRequestDto(body);

        HashMap<String, String> savedEntity = registerUserCase.execute(userDomain);

        if (savedEntity != null){
            return ResponseEntity.status(200).body(userMapper.toLoginRegisterResponseDto(savedEntity));
        }
        return ResponseEntity.status(409).body("nome de usuário ou email já cadastrado");
    }

    @PostMapping("/login")
    @Operation(
            summary = "Realiza o login do usuário",
            description = "Valida as credenciais enviadas e retorna um token JWT para autenticação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequestDTO body){
        User userDomain = userMapper.toDomainFromLoginRequstDTO(body);

        try {
            HashMap<String,String> response = loginUserCase.execute(userDomain);
            if (response != null){
                return ResponseEntity.status(200).body(userMapper.toLoginRegisterResponseDto(response));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
        return ResponseEntity.status(400).body("Credenciais inválidas");
    }

    // ENDPONITS DE TESTE, remover assim que não forem mais nescessários
    @GetMapping("/teste-user")
    public ResponseEntity<?> testeUser(){
        return ResponseEntity.ok().body("usuaio comum e admin podem usar este endpoint");
    }

    @GetMapping("/teste-admin")
    public ResponseEntity<?> testeAdmin(){
        return ResponseEntity.ok().body("apenas admin pode usar este endpoint");
    }
}
