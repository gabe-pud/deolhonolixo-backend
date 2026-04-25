package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.usecase.city.UrbanGetGeometryUseCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.UrbanGeometryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/urban-geometry")
@RequiredArgsConstructor
@Tag(name = "Urban Geometry", description = "Endpoints para bairros e ruas de Praia Grande")
public class UrbanGeometryController {
    private final UrbanGetGeometryUseCase useCase;
    private final UrbanGeometryMapper mapper;

    @GetMapping
    @Operation(summary = "Lista todos os bairros e ruas")
    public ResponseEntity<?> listarTodos() {
        var result = useCase.execute()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca bairro ou rua por ID")
    public ResponseEntity<?> buscarPorId(@PathVariable String  id) {
        return ResponseEntity.ok(mapper.toResponseDTO(useCase.executeById(id)));
    }
}
