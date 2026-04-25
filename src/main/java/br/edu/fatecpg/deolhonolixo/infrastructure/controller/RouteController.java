package br.edu.fatecpg.deolhonolixo.infrastructure.controller;


import br.edu.fatecpg.deolhonolixo.core.usecase.city.RouteGetUseCase;

import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.RouteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
@Tag(name = "Rotas", description = "Endpoints para rotas de coleta de lixo")
public class RouteController {
    private final RouteGetUseCase useCase;
    private final RouteMapper mapper;

    @GetMapping
    @Operation(summary = "Lista todas as rotas")
    public ResponseEntity<?> listarTodas() {
        var result = useCase.execute()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca rota por ID")
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(mapper.toResponseDTO(useCase.executeById(id)));
    }
}
