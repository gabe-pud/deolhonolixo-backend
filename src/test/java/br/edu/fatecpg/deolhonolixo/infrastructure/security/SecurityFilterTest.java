package br.edu.fatecpg.deolhonolixo.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityFilterTest {
    @Mock
    private TokenService tokenService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;

    @InjectMocks
    private SecurityFilter securityFilter;

    @BeforeEach
    void before() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void after() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldSetAuthenticationWhenTokenValid() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token123");

        JWTUserData userData = new JWTUserData(7L, "ana@example.com", List.of("ROLE_USER"));
        when(tokenService.validateToken("token123")).thenReturn(Optional.of(userData));

        securityFilter.doFilterInternal(request, response, chain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth);
        assertEquals(userData, auth.getPrincipal());
        assertTrue(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));

        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldNotSetAuthenticationWhenHeaderMissing() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        securityFilter.doFilterInternal(request, response, chain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNull(auth);

        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldNotSetAuthenticationWhenTokenInvalid() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token123");
        when(tokenService.validateToken("token123")).thenReturn(Optional.empty());

        securityFilter.doFilterInternal(request, response, chain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNull(auth);
        verify(chain, times(1)).doFilter(request, response);
    }
}
