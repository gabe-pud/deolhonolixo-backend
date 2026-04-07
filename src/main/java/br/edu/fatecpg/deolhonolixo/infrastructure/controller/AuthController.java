package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.ConfirmPasswordMismatchExeption;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.LoginValidationException;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.UserAlreadyRegisteredException;
import br.edu.fatecpg.deolhonolixo.core.usecase.user.ConfirmPasswordMatchCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.user.LoginCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.user.RegisterCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.UserLoginRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.UserRegisterRequestDTO;
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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Autenticação",
        description = "Endpoints responsáveis pela autenticação, registro e verificação de usuários."
)
public class AuthController {
    private final UserMapper userMapper;
    private final RegisterCase registerCase;
    private final LoginCase loginCase;
    private final ConfirmPasswordMatchCase passwordMatchUserCase;

    @PostMapping("/register")
    @Operation(
            summary = "Registra um novo usuário",
            description = "Cria um novo usuário no sistema, envia o código de verificação por e-mail e retorna uma mensagem de confirmação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro realizado com sucesso, verifique seu e-mail"),
            @ApiResponse(responseCode = "409", description = "Nome de usuário ou email já cadastrado"),
            @ApiResponse(responseCode = "422", description = "A confirmação de senha não confere com a senha digitada")
    })
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequestDTO body){
        try {
            passwordMatchUserCase.execute(body.password(), body.confirmPassword());
        } catch (ConfirmPasswordMismatchExeption e) {
            return ResponseEntity.status(422).body(e.getMessage());
        }

        User userDomain = userMapper.toDomainFromRegisterRequestDto(body);

        try {
            HashMap<String, String> response = registerCase.execute(userDomain);
            return ResponseEntity.status(200).body(userMapper.toLoginRegisterResponseDto(response));
        } catch (UserAlreadyRegisteredException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(
            summary = "Realiza o login do usuário",
            description = "Valida as credenciais enviadas e retorna um token JWT para autenticação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas")
    })
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequestDTO body){
        User userDomain = userMapper.toDomainFromLoginRequstDTO(body);

        try {
            HashMap<String,String> response = loginCase.execute(userDomain);
            return ResponseEntity.status(200).body(userMapper.toLoginRegisterResponseDto(response));
        } catch (LoginValidationException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
