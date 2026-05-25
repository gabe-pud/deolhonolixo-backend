package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.Route;
import br.edu.fatecpg.deolhonolixo.core.usecase.city.RouteFindAllCase;

import br.edu.fatecpg.deolhonolixo.core.usecase.city.RouteFindByRouteIdCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.city.RouteSaveCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.RouteSaveRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.RouteResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.RouteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
@Tag(name = "Rotas", description = "Endpoints para rotas de coleta de lixo")
public class RouteController {
    private final RouteFindAllCase findAllCase;
    private final RouteFindByRouteIdCase findByRouteIdCase;
    private final RouteSaveCase saveCase;
    private final RouteMapper mapper;

    @GetMapping
    @Operation(summary = "Lista todas as rotas")
    public ResponseEntity<List<RouteResponseDTO>> listarTodas() {
        List<RouteResponseDTO> response = findAllCase.execute()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca rota por ID")
    public ResponseEntity<RouteResponseDTO> buscarPorId(@PathVariable String id) {
        RouteResponseDTO response = mapper.toResponseDTO(findByRouteIdCase.execute(id));

        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Cria uma rota")
    public ResponseEntity<RouteResponseDTO> salvar(@Valid @RequestBody RouteSaveRequestDTO request) {
        Route savedRoute = saveCase.execute(mapper.toDomainFormRequestDTO(request));

        RouteResponseDTO response = mapper.toResponseDTO(savedRoute);

        return ResponseEntity.ok(response);
    }
}
