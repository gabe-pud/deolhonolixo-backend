package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.User;
import br.edu.fatecpg.deolhonolixo.core.domain.Role;
import br.edu.fatecpg.deolhonolixo.core.usecase.user.ConfirmPasswordMatchCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.user.LoginCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.user.LoginCaseOutputDTO;
import br.edu.fatecpg.deolhonolixo.core.usecase.user.RegisterCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.UserLoginRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.UserRegisterRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.UserLoginAndRegisterResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.ConfirmPasswordMismatchException;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.LoginValidationException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    UserMapper userMapper;

    @Mock
    RegisterCase registerCase;

    @Mock
    LoginCase loginCase;

    @Mock
    ConfirmPasswordMatchCase passwordMatchUserCase;

    @InjectMocks
    AuthController controller;

    @Test
    void register_returns201() {
        UserRegisterRequestDTO req = new UserRegisterRequestDTO("u","e@mail.com","pass","pass", java.util.Set.of(Role.ROLE_USER));
        User domainUser = new User(1L, "u", "e@mail.com", "pass", java.util.Set.of(Role.ROLE_USER), Boolean.TRUE);
        UserLoginAndRegisterResponseDTO respDto = new UserLoginAndRegisterResponseDTO("u","tok");

        doNothing().when(passwordMatchUserCase).execute(req.password(), req.confirmPassword());
        when(userMapper.toDomainFromRegisterRequestDto(req)).thenReturn(domainUser);
        when(registerCase.execute(domainUser)).thenReturn(domainUser);
        when(userMapper.toRegisterResponseDto(domainUser)).thenReturn(respDto);

        var resp = controller.register(req);

        assertEquals(201, resp.getStatusCode().value());
        assertEquals("u", resp.getBody().username());
    }

    @Test
    void login_returns200() {
        UserLoginRequestDTO req = new UserLoginRequestDTO("e@mail.com","pass");
        LoginCaseOutputDTO out = new LoginCaseOutputDTO("u","tok");
        UserLoginAndRegisterResponseDTO respDto = new UserLoginAndRegisterResponseDTO("u","tok");

        when(loginCase.execute(req.email(), req.password())).thenReturn(out);
        when(userMapper.toLoginResponseDto(out)).thenReturn(respDto);

        var resp = controller.login(req);

        assertEquals(200, resp.getStatusCode().value());
        assertEquals("tok", resp.getBody().token());
    }

    @Test
    void register_passwordMismatch_throws() {
        UserRegisterRequestDTO req = new UserRegisterRequestDTO("u","e@mail.com","p1","p2", Set.of(br.edu.fatecpg.deolhonolixo.core.domain.Role.ROLE_USER));

        doThrow(new ConfirmPasswordMismatchException()).when(passwordMatchUserCase).execute(req.password(), req.confirmPassword());

        assertThrows(ConfirmPasswordMismatchException.class, () -> controller.register(req));
    }

    @Test
    void login_invalidCredentials_throws() {
        UserLoginRequestDTO req = new UserLoginRequestDTO("e@mail.com","wrong");

        when(loginCase.execute(req.email(), req.password())).thenThrow(new LoginValidationException());

        assertThrows(LoginValidationException.class, () -> controller.login(req));
    }
}
